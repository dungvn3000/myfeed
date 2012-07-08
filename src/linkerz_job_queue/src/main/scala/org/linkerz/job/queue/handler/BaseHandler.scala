/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core._
import collection.mutable.ListBuffer
import util.control.Breaks._
import grizzled.slf4j.Logging

/**
 * The Class BaseHandler.
 * The Handler with workers, the job will be do async in here.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:56 AM
 *
 */

abstract class BaseHandler[J <: Job, S <: Session] extends HandlerInSession[J, S] with Logging {

  val workers = new ListBuffer[Worker[J, S]]

  var subJobQueue = new Queue[J] with ScalaQueue[J]

  var retryCount = 0
  var maxRetry = 100

  protected def doHandle(job: J, session: S) {
    addSubJobs(workers.head.work(job, session))
    doSubJobs(session)
  }

  private def doSubJobs(session: S) {
    breakable {
      while (true) {
        subJobQueue.next() match {
          case Some(job) => doSubJob(job, session)
          case None => break()
        }
      }
    }
  }

  private def doSubJob(job: J, session: S): Boolean = {
    var isDone = false
    breakable {
      workers.foreach(worker => if (worker.isFree) {
        addSubJobs(worker.work(job, session))
        isDone = true
        break()
      })
    }
    if (!isDone) {
      info("It seem all workers is busy now...")
      //Re add the job.
      subJobQueue += job
      //Sleep 1s waiting for workers
      Thread.sleep(1000)

      //Count time to retry, if it is too much, stop the handler and report error to controller.
      retryCount += 1
      if (retryCount >= maxRetry) {
        subJobQueue.clear()
      }
    }
    isDone
  }

  private def addSubJobs(subJobs: List[J]) {
    if (!subJobs.isEmpty) {
      subJobQueue ++= subJobs
    }
  }

}
