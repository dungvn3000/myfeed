/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

import actors.Actor
import org.linkerz.job.queue.controller.{STOP, NEXT}
import scala.None


/**
 * The Class Worker.
 *
 * Worker will do the job in async.
 * At the same time the worker hold only one job.
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:53 AM
 *
 */

trait Worker[J <: Job, S <: Session] extends CallBackable[List[J]] {

  var _callback: CallBack[List[J]] = _

  var currentJob: Option[J] = None
  var currentSession: Option[S] = None

  var actor = new Actor {
    def act() {
      loop {
        react {
          case NEXT => {
            try {
              val result = Some(analyze(currentJob.get, currentSession.get))
              if (callback != null) callback.onSuccess(this, result)
            } catch {
              case e: Exception => if (callback != null) callback.onFailed(this, e)
            } finally {
              //Clear job and session.
              currentJob = None
              currentSession = None
            }
          }
          case STOP => {
            reply("STOP")
            exit()
          }
        }
      }
    }
  }

  actor.start()

  /**
   * The handler will check the worker is free or not, if he free, he has to work
   * @return
   */
  def isFree: Boolean = currentJob.isEmpty

  /**
   * Analyze and do the job, if it has SubJobs then tell handler.
   * This action will be done in sync.
   * @param job
   * @param session
   * @return list of sub job.
   */
  @throws(classOf[Exception])
  def analyze(job: J, session: S): List[J]

  /**
   * Hard Work for the Job.
   * This action will be done in async.
   * @param job
   * @param session
   */
  def work(job: J, session: S) {
    currentJob = Some(job)
    currentSession = Some(session)
    actor ! NEXT
  }

  /**
   * Tell the worker stop and waiting for the worker stop.
   */
  def stop() {
    actor !? STOP
  }

  def callback = _callback

  def callback_=(callback: CallBack[List[J]]) {
    _callback = callback
  }
}