/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

import grizzled.slf4j.Logging
import scalaz.Scalaz._
import scalaz.concurrent.{Actor, Strategy}


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

  private var _callback: CallBack[J] = _
  private var _isFree = true

  case class NEXT(job: J, session: S)

  var workerActor: Actor[NEXT] = _

  /**
   * Create actor for the worker, this method must be called before the worker is going to work.
   * @param strategy
   */
  def createActor(strategy: Strategy) {
    workerActor = actor {
      (next: NEXT) => {
        try {
          //Analyze the job
          analyze(next.job, next.session)
          if (!next.job.parent.isEmpty) {
            //If this is a subJob, copy error and info from subJob to the first job.
            val session = next.session
            val job = session.job
            val subJob = next.job

            subJob.error.foreach(error => job.error += error)
            subJob.info.foreach(info => job.info += info)
          }
          if (callback != null) callback.onSuccess(Worker.this, new Some(next.job))
        } catch {
          case e: Exception => if (callback != null) callback.onFailed(Worker.this, e)
        } finally {
          _isFree = true
        }
      }
    } (strategy)
  }


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
    //mark the work was taken the job.
    _isFree = false

    workerActor ! NEXT(job, session)
  }

  def callback = _callback

  def callback_=(callback: CallBack[J]) {
    _callback = callback
  }
}