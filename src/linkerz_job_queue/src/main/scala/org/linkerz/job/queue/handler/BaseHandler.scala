/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.handler

import org.linkerz.job.queue.core._
import collection.mutable.ListBuffer
import util.control.Breaks._

/**
 * The Class BaseHandler.
 * The Handler with workers, the job will be do async in here.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:56 AM
 *
 */

abstract class BaseHandler[J <: Job] extends HandlerInSession[J, Session] {

  val workers = new ListBuffer[Worker[J]]

  var subJobQueue = new Queue[J] with ScalaQueue[J]

  protected def doHandle(job: J, session: Session) {
    addSubJobs(workers.head.work(job, null))
    doSubJobs()
  }

  private def doSubJobs() {
    breakable {
      while (true) {
        subJobQueue.next() match {
          case Some(job) => doSubJob(job)
          case None => break()
        }
      }
    }
  }

  private def doSubJob(job: J) {
    breakable {
      workers.foreach(worker => if (worker.isFree) {
        addSubJobs(worker.work(job, null))
      } else {
        break()
      })
    }
  }

  private def addSubJobs(subJobs: List[J]) {
    if (!subJobs.isEmpty) {
      subJobQueue ++= subJobs
    }
  }

}
