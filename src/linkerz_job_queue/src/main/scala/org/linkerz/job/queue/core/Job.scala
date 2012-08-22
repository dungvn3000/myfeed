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
   * The retry time when the worker is busy.
   */
  var maxRetry: Int = 100

  /**
   * Sleep time waiting for the working worker.
   */
  var ideTime: Int = 1000

  /**
   * Politeness delay in milliseconds (delay between sending two requests to
   * the same job parent).
   */
  var politenessDelay: Int = 0

  /**
   * This setting only for async handler.
   */
  var numberOfWorker: Int = 1

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