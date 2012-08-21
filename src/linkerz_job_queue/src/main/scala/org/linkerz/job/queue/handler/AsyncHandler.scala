/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core._
import collection.mutable.ListBuffer
import util.control.Breaks._
import grizzled.slf4j.Logging
import reflect.BeanProperty
import Job._

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
  protected var jobConfig: Map[String, AnyRef] = _

  protected var subJobQueue = new Queue[J] with ScalaQueue[J]

  protected val workers = new ListBuffer[Worker[J, S]]

  @BeanProperty
  var maxRetry = 100

  //Time for waiting when all worker are busy. Time unit is ms.
  @BeanProperty
  var ideTime = 1000

  /**
   * Politeness delay in milliseconds (delay between sending two requests to
   * the same job parent).
   */
  @BeanProperty
  var politenessDelay = 0

  @BeanProperty
  var numberOfWorker = 1

  protected def doHandle(job: J, session: S) {
    //Read Job Config
    readJobConfig(job)

    createWorker(numberOfWorker)

    //Make sure the handler at least has one worker.
    assert(workers.size > 0)

    //Step 1: Analyze the job first,
    //check the result then decide will continue or not.
    workers.head.analyze(job, session)
    createSubJobs(job)


    //Step 2: Working on the sub job.
    //Hook to the worker
    workers.foreach(worker => worker.callback = this)
    doSubJobs(session)
  }

  private def doSubJobs(session: S) {
    breakable {
      while (true) {
        subJobQueue.next() match {
          case Some(job) => doSubJob(job, session)
          case None => {
            //There is no more job. Check all worker if they are free, finish the job.
            if (workers.filter(worker => !worker.isFree).size == 0) {
              stopWorker()
              logger.info("No more job and all workers is free. Finish....")
              break()
            } else {
              //No more job, but some worker is still doing, sleep 1s waiting for them.
              Thread.sleep(1000)
            }
          }
        }
      }
    }
  }

  private def doSubJob(job: J, session: S): Boolean = {
    var isDone = false
    breakable {
      workers.foreach(worker => if (worker.isFree) {
        worker.work(job, session)
        if (politenessDelay > 0) {
          //Delay time for each job.
          Thread.sleep(politenessDelay)
        }
        isDone = true
        break()
      })
    }
    if (!isDone) {
      info("It seem all workers are busy now...")
      //Re add the job.
      subJobQueue += job
      //Sleep 1s waiting for workers
      Thread.sleep(ideTime)

      //Count time to retry, if it is too much, stop the handler and report error to controller.
      _retryCount += 1
      if (_retryCount >= maxRetry) {
        subJobQueue.clear()
        stopWorker()
        info("Stop because all workers can't finish it job.")
      }
    }
    isDone
  }

  /**
   * Stop all worker life and remove it from the handler.
   */
  private def stopWorker() {
    workers.foreach(worker => worker.stop())
    workers.clear()
  }

  /**
   * Read config from job. Just in case for each job have different setting.
   * Note: The default setting will be change with the last Job Config.
   * @param job
   */
  protected def readJobConfig(job: J) {
    //Save job config form the job. If it not exist using default value
    jobConfig = job.jobConfig
    if (!jobConfig.isEmpty) {
      if (!jobConfig.get(MAX_RETRY).isEmpty) {
        maxRetry = jobConfig.get(MAX_RETRY).get.asInstanceOf[Int]
      }
      if (!jobConfig.get(IDE_TIME).isEmpty) {
        ideTime = jobConfig.get(IDE_TIME).get.asInstanceOf[Int]
      }
      if (!jobConfig.get(POLITENESS_DELAY).isEmpty) {
        politenessDelay = jobConfig.get(POLITENESS_DELAY).get.asInstanceOf[Int]
      }
      if (!jobConfig.get(NUMBER_OF_WORKER).isEmpty) {
        numberOfWorker = jobConfig.get(NUMBER_OF_WORKER).get.asInstanceOf[Int]
      }
    }
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
