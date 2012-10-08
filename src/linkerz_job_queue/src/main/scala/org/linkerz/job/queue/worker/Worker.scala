/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.worker

import grizzled.slf4j.Logging
import org.linkerz.job.queue.core.{Session, Job}
import akka.actor.Actor
import org.linkerz.job.queue.handler.AsyncHandler.{Stop, Fail, Success, Next}

/**
 * The Class Worker.
 *
 * Worker will do the job in async.
 * At the same time the worker hold only one job. After finish that job it's gonna take another job.
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:53 AM
 *
 */
trait Worker[J <: Job, S <: Session[J]] extends Actor with Logging {

  protected def receive = {
    case next: Next[J, S] => {
      try {
        //Analyze the job
        work(next.job, next.session)
        if (!next.job.parent.isEmpty) {
          //If this is a subJob, copy error and info from subJob to the first job.
          val session = next.session
          val job = session.job
          val subJob = next.job

          subJob.error.foreach(error => job.error += error)
          subJob.info.foreach(info => job.info += info)
        }
        sender ! Success(next.job)
      } catch {
        case ex: Exception => sender ! Fail(next.job, ex)
      }
    }
    case Stop => {
      sender ! "stop"
      context.stop(self)
    }
  }

  /**
   * Analyze and do the job and return the result.
   * This action will be done in sync.
   * @param job
   * @param session
   */
  @throws(classOf[Exception])
  def work(job: J, session: S)
}