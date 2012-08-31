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

  private var _retryCount = 0

  /**
   * The handler will stop when it turn on.
   */
  protected var isStop = false
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
  var workerManager: Actor[J] = _

  protected def doHandle(job: J, session: S) {
    currentSession = session
    currentJob = job

    //Create Thread pool for worker
    val threadPool = Executors.newCachedThreadPool()
    val strategy = Strategy.Executor(threadPool)

    //Create worker manager.
    workerManager = actor {
      (job: J) => {
        doSubJob(job)
      }
    } (strategy)

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

    //Step 4: Finish and reset.
    threadPool.shutdown()
    threadPool.awaitTermination(60L, TimeUnit.SECONDS)
    workers.clear()
    _retryCount = 0
    isStop = false
  }

  private def waitForFinish() {
    while (!isStop) {
      //Check all worker if they are free, finish the job.
      if (workers.filter(worker => !worker.isFree).size == 0) {
        //waiting for 10s and recheck again
        info("All worker are free, waiting for 10s and recheck")
        Thread.sleep(1000 * 10)
        if (workers.filter(worker => !worker.isFree).size == 0) {
          logger.info("It seem is no more job and all workers is free. Finish....")
          isStop = true
        }
      } else {
        //No more job, but some worker is still doing, sleep 1s waiting for them.
        Thread.sleep(1000)
      }
    }
  }

  private def doSubJob(job: J): Boolean = {
    var isWorking = false
    breakable {
      workers.foreach(worker => if (worker.isFree) {
        worker.work(job, currentSession)
        //Delay time for each job.
        if (currentJob.politenessDelay > 0) Thread.sleep(currentJob.politenessDelay)
        isWorking = true
        break()
      })
    }
    if (!isWorking) {
      info("It seem all workers are busy now...")
      //Re add the job.
      workerManager ! job
      //Sleep 1s waiting for workers
      Thread.sleep(currentJob.ideTime)

      //Count time to retry, if it is too much, stop the handler and report error to controller.
      _retryCount += 1
      if (_retryCount >= currentJob.maxRetry) {
        isStop = true
        info("Stop because all workers can't finish it job.")
      }
    }
    isWorking
  }

  /**
   * Create worker for handler.
   * @param numberOfWorker
   */
  protected def createWorker(numberOfWorker: Int)


  def onFailed(source: Any, ex: Exception) {
    error(ex.getMessage, ex)
  }


  def onSuccess(source: Any, result: Option[J]) {
    if (!result.isEmpty) createSubJobs(result.get)
  }

  /**
   * Create a sub job base the result of a job.
   * @param job
   */
  protected def createSubJobs(job: J)

  /**
   * Getter for _retryCount
   * @return
   */
  def retryCount = _retryCount
}
