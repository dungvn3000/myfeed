/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

/**
 * The Class HandlerInSession.
 * For a Handler want to run in session.
 *
 * @author Nguyen Duc Dung
 * @since 7/9/12, 3:54 AM
 *
 */

trait HandlerInSession[J <: Job, S <: Session[J]] extends Handler[J] {

  /**
   * Not using this method.
   * @param job
   */
  override final def handle(job: Job) { throw new Exception("Medthod Unsuport")}

  /**
   * Not using this method
   * @param job
   */
  protected final def doHandle(job: J) {throw new Exception("Medthod Unsuport")}

  /**
   * Return session class.
   * @return
   */
  def sessionClass: Class[S]

  /**
   * Handel for the job in session.
   * @param job
   * @param session
   */
  def handle(job: Job, session: S) {
    doHandle(job.asInstanceOf[J], session)
  }

  /**
   * Handel the job in session.
   * @param job
   * @param session
   */
  protected def doHandle(job: J, session: S)
}
