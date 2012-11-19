/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.core

import collection.mutable.ListBuffer
import org.linkerz.model.{LoggingDao, Logging}

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
trait Job extends Serializable {

  protected var _error = new ListBuffer[Logging]
  protected var _warn = new ListBuffer[Logging]
  protected var _info = new ListBuffer[Logging]

  /**
   * The depth of the job from the first job.
   */
  private var _depth: Int = 0

  /**
   * Max depth for a crawl job, default is umlimted.
   */
  var maxDepth: Int = -1

  /**
   * Time out for working on the job, default is unlimted.
   */
  var timeOut = -1

  /**
   * Politeness delay in milliseconds (delay between sending two requests to
   * the same job parent).
   */
  var politenessDelay: Int = 0

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

  def warn = _warn

  /**
   * For debug information.
   * @param msg
   */
  def warn(msg: String, className: String) {
    _warn += Logging(
      message = msg,
      className = className,
      logType = "warn"
    )
  }

  /**
   * For debug information.
   * @param msg
   */
  def info(msg: String, className: String) {
    _info += Logging(
      message = msg,
      className = className,
      logType = "info"
    )
  }

  /**
   * For detect error.
   * @param msg
   */
  def error(msg: String, className: String) {
    status = JobStatus.ERROR
    _error += Logging(
      message = msg,
      className = className,
      logType = "error"
    )
  }

  /**
   * For detect error.
   * @param msg
   * @param ex Throwable.
   */
  def error(msg: String, className: String, ex: Throwable) {
    status = JobStatus.ERROR
    _error += Logging(
      message = msg,
      className = className,
      exceptionClass = Some(ex.getClass.getName),
      logType = "error"
    )
  }


  def depth: Int = {
    if (!parent.isEmpty) {
      _depth = parent.get.depth + 1
    }
    _depth
  }
}