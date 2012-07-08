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
 * Handle of the job.
 * @tparam J job
 */
trait Handler[J <: Job] {

  /**
   * Check is this hanlder is for the job or not.
   * @param job
   * @return false if it is not.
   */
  def accept(job: Job): Boolean

  /**
   * Handel for the job.
   * @param job
   */
  def handle(job: Job) {
    doHandle(job.asInstanceOf[J])
  }

  /**
   * Handel the job
   * @param job
   */
  protected def doHandle(job: J)

}