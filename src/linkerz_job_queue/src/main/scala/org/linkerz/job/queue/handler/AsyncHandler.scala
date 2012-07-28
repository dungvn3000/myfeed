/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core._
import collection.mutable.ListBuffer
import util.control.Breaks._
import grizzled.slf4j.Logging

/**
 * The Class AsyncHandler.
 * The Handler with workers, the job will be do async in here.
 * Make sure the handler always has at least one worker.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:56 AM
 *
 */

abstract class AsyncHandler[J <: Job, S <: Session] extends HandlerInSession[J, S] with CallBack[List[J]] with Logging {

  val workers = new ListBuffer[Worker[J, S]]

  var subJobQueue = new Queue[J] with ScalaQueue[J]

  var retryCount = 0

  var maxRetry = 100

  protected def doHandle(job: J, session: S) {
    addSubJobs(workers.head.analyze(job, session))
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
        isDone = true
        break()
      })
    }
    if (!isDone) {
      info("It seem all workers are busy now...")
      //Re add the job.
      subJobQueue += job
      //Sleep 1s waiting for workers
      Thread.sleep(1000)

      //Count time to retry, if it is too much, stop the handler and report error to controller.
      retryCount += 1
      if (retryCount >= maxRetry) {
        subJobQueue.clear()
        workers.foreach(worker => worker.stop())
        workers.clear()
        info("Stop because all workers can't finish it job.")
      }
    }
    isDone
  }


  def onFailed(source: Any, ex: Exception) {
    error(ex.getMessage, ex)
  }


  def onSuccess(source: Any, result: Option[List[J]]) {
    info("Callback form " + source)
    if (!result.isEmpty) addSubJobs(result.get)
  }

  private def addSubJobs(subJobs: List[J]) {
    if (subJobs!= null && !subJobs.isEmpty) {
      subJobQueue ++= subJobs
    }
  }

}
