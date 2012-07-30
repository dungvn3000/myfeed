/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

/**
 * The Class Job.
 *
 * @author Nguyen Duc Dung
 * @since 7/7/12, 10:21 PM
 *
 */

/**
 * The trait job represent for a job. It can be add to a Job Queue.
 */
trait Job {

  /**
   * Return the result of the job.
   * @return
   */
  def result: Option[Any]

  /**
   * The parent job.
   * @return
   */
  def parent: Option[Job] = None
}