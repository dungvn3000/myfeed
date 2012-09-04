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
   * The depth of the job from the first job.
   */
  private var _depth: Int = 0

  /**
   * Max depth for a crawl job.
   */
  var maxDepth: Int = 1

  /**
   * Time out for working on the job, default is 10s
   */
  var timeOut = 10000

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

  //Check whether the job is error or not.
  def isError = !error.isEmpty

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


  def depth: Int = {
    if (!parent.isEmpty) {
      _depth = parent.get.depth + 1
    }
    _depth
  }
}