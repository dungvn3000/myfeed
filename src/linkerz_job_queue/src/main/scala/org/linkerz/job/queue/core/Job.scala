/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.core

import collection.mutable.ListBuffer

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

  private var _error = new ListBuffer[(String, Throwable)]
  private var _info = new ListBuffer[String]

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
   * The status of the job.
   */
  var status: String = JobStatus.NEW

  /**
   * Maximum number of sub job, the job can have.
   * -1 is unlimited.
   */
  var maxSubJob: Int = -1

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

  def error = _error

  def info = _info

  /**
   * For debug information.
   * @param msg
   */
  def info(msg: String) {
    _info += msg
  }

  /**
   * For detect error.
   * @param msg
   */
  def error(msg: String) {
    status = JobStatus.ERROR
    _error += Tuple2(msg, null)
  }

  /**
   * For detect error.
   * @param msg
   * @param ex Throwable.
   */
  def error(msg: String, ex: Throwable) {
    status = JobStatus.ERROR
    _error += Tuple2(msg, ex)
  }
}