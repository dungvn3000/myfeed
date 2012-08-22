/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

import actors.Actor
import grizzled.slf4j.Logging

/**
 * The Class Worker.
 *
 * Worker will do the job in async.
 * At the same time the worker hold only one job. After finish that job it's gonna take another job.
 * @author Nguyen Duc Dung
 * @since 7/9/12, 1:53 AM
 *
 */
trait Worker[J <: Job, S <: Session[J]] extends CallBackable[J] with Logging {

  case object STOP

  case class NEXT(job: J, session: S)

  private var _callback: CallBack[J] = _
  private var _isFree = true

  var actor = new Actor {
    def act() {
      loop {
        react {
          case NEXT(job, session) => {
            try {
              //Analyze the job
              analyze(job, session)
              if (callback != null) callback.onSuccess(this, new Some(job))
            } catch {
              case e: Exception => if (callback != null) callback.onFailed(this, e)
            } finally {
              _isFree = true
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
  def isFree: Boolean = _isFree

  /**
   * Analyze and do the job and return the result.
   * This action will be done in sync.
   * @param job
   * @param session
   */
  @throws(classOf[Exception])
  def analyze(job: J, session: S)

  /**
   * Hard Working for the Job.
   * This action will be done in async.
   * @param job
   * @param session
   */
  def work(job: J, session: S) {
    _isFree = false
    actor ! NEXT(job, session)
  }

  /**
   * Tell the worker stop and waiting for the worker stop.
   */
  def stop() {
    actor !? STOP
  }

  def callback = _callback

  def callback_=(callback: CallBack[J]) {
    _callback = callback
  }
}