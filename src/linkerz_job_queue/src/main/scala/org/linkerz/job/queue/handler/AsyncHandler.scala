/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core._
import collection.mutable.ListBuffer
import util.control.Breaks._
import grizzled.slf4j.Logging
import scalaz.Scalaz._
import java.util.concurrent.{TimeUnit, Executors}
import scalaz.concurrent.{Actor, Strategy}

/**
 * The Class AsyncHandler.
 * The Handler with workers, the job will be do async in here.
 * Make sure the handler always has at least one worker.
 * The Worker will be created before handle a job.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:56 AM
 *
 */

abstract class AsyncHandler[J <: Job, S <: Session[J]] extends HandlerInSession[J, S] with CallBack[J] with Logging {

  //Counting child jobs was done by the current job.
  private var _subJobCount: Int = _

  def subJobCount = _subJobCount

  //Starting time on current job.
  protected var startTime: Long = _

  /**
   * The handler will stop when it turn on.
   */
  protected var isStop: Boolean = _
  protected val workers = new ListBuffer[Worker[J, S]]

  /**
   * The current session.
   */
  protected var currentSession: S = _

  /**
   * The current job inside the session.
   */
  protected var currentJob: J = _

  //The manager of all workers.
  var workerManager: Actor[J] = actor {
    (job: J) => {
      if (!isStop) {
        try {
          doSubJob(job)
        } catch {
          case ex: Exception => {
            error(ex.getMessage, ex)
            currentJob.error(ex.getMessage, ex)
          }
        }
      }
    }
  }

  protected def doHandle(job: J, session: S) {
    //Start counting time.
    startTime = System.currentTimeMillis

    //Reset to start
    isStop = false
    workers.clear()
    _subJobCount = 0

    currentSession = session
    currentJob = job

    //Create Thread pool for worker
    val threadPool = Executors.newCachedThreadPool()
    val strategy = Strategy.Executor(threadPool)

    //Create worker and create actor for it.
    createWorker(currentJob.numberOfWorker)
    workers.foreach(worker => worker.createActor(strategy))

    //Make sure the handler at least has one worker.
    assert(workers.size > 0)

    //Step 1: Analyze the job first,
    //check the result then decide will continue or not.
    workers.head.analyze(job, session)

    //Step 2: Working on the sub job.
    //Hook to the worker
    workers.foreach(worker => worker.callback = this)
    createSubJobs(job)

    //Step 3: Waiting for all worker finished their job
    waitForFinish()

    //Step 4: Finish.
    onFinish()
    threadPool.shutdown()
    info("Waiting for all workers stoped")
    threadPool.awaitTermination(60L, TimeUnit.SECONDS)
    onFinished()
  }

  private def waitForFinish() {
    while (!isStop) {
      //Check all worker if they are free, finish the job.
      if (workers.filter(worker => !worker.isFree).size == 0) {
        //waiting for 3s and recheck again
        info("All worker are free, waiting for 5s and recheck")
        Thread.sleep(1000 * 5)
        if (workers.filter(worker => !worker.isFree).size == 0) {
          logger.info("It seem is no more job and all workers is free. Finish....")
          isStop = true
        }
      } else {
        //Worker is doing, sleep 1s waiting for them.
        Thread.sleep(1000)
      }

      //Checking working time.
      if (currentJob.timeOut > 0) {
        val time = System.currentTimeMillis - startTime
        if (time > currentJob.timeOut) {
          info("Stop because the time is out")
          //marking the job is error
          currentJob.error("Time Out")
          isStop = true
        }
      }
    }
  }

  private def doSubJob(job: J): Boolean = {
    var isWorking = false
    breakable {
      if (currentJob.maxSubJob >= 0 && _subJobCount >= currentJob.maxSubJob) {
        info("Stop because the number of sub job reached maximum")
        isStop = true
        return false
      }
      workers.foreach(worker => if (worker.isFree) {
        worker.work(job, currentSession)
        //Delay time for each job.
        if (currentJob.politenessDelay > 0) Thread.sleep(currentJob.politenessDelay)
        isWorking = true
        _subJobCount += 1
        break()
      })
    }
    if (!isWorking) {
      //Re add the job.
      workerManager ! job
      //Sleep 1s waiting for workers
      Thread.sleep(1000)
    }
    isWorking
  }

  /**
   * This method will be called before the hander finish everything.
   */
  protected def onFinish() {}

  /**
   * This method will be called after the handler finished everything.
   */
  protected def onFinished() {}

  /**
   * Create worker for handler.
   * @param numberOfWorker
   */
  protected def createWorker(numberOfWorker: Int)


  def onFailed(source: Any, ex: Exception) {
    error(ex.getMessage, ex)
    currentJob.error(ex.getMessage, ex)
  }


  def onSuccess(source: Any, result: Option[J]) {
    if (!result.isEmpty) createSubJobs(result.get)
  }

  /**
   * Create a sub job base the result of a job.
   * @param job
   */
  protected def createSubJobs(job: J)
}
