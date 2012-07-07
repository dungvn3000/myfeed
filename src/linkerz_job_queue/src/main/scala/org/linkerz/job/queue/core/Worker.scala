/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

/**
 * The Class Worker.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 10:33 PM
 *
 */

/**
 * Worker of the job.
 * @tparam J job
 */
trait Worker[J <: Job] {

  /**
   * Check is this worker is for the job or not.
   * @param job
   * @return false if it is not.
   */
  def isFor(job: Job): Boolean = {
    job.isInstanceOf[J]
  }

  /**
   * Work for the job in session.
   * @param job
   * @param session
   */
  def work(job: Job, session: Session) {
    doWork(job.asInstanceOf[J], session)
  }

  /**
   * Do the job
   * @param job
   * @param session
   */
  def doWork(job: J, session: Session)

}