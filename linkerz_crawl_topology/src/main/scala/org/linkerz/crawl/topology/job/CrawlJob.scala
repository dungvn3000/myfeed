/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.job

import org.linkerz.crawl.topology.model.{WebPage, WebUrl}
import scala.Some
import java.util.regex.Pattern
import org.linkerz.model.{NewFeed, Logging}
import collection.mutable.ListBuffer

/**
 * The Class CrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:55 AM
 *
 */

case class CrawlJob(webUrl: WebUrl) {

  var parent: Option[CrawlJob] = None

  var result: Option[WebPage] = None

  var onlyCrawlInSameDomain: Boolean = true

  //File Filter
  var filter = ".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4" +
    "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$"


  def filterPattern = Pattern.compile(filter)

  /**
   * Only crawl the url match with this regex.
   */
  var urlRegex: Option[String] = None

  /**
   * For those of url match with this regex will not be crawl.
   */
  var excludeUrl: List[String] = Nil

  /**
   * Max depth for a crawl job, default is umlimted.
   */
  var maxDepth: Int = -1

  /**
   * Politeness delay in milliseconds (delay between sending two requests to
   * the same job parent).
   */
  var politenessDelay: Int = 0

  /**
   * Maximum number of sub job, the job can have.
   * -1 is unlimited.
   */
  var maxSubJob: Int = -1

  /**
   * String url.
   * @param url
   */
  def this(url: String) {
    this(new WebUrl(url))
  }

  /**
   *
   * @param newFeed
   */
  def this(newFeed: NewFeed) {
    this(newFeed.url)

    if (!newFeed.urlRegex.isEmpty) {
      urlRegex = Some(newFeed.urlRegex)
    }

    if (!newFeed.excludeUrl.isEmpty) {
      excludeUrl = newFeed.excludeUrl
    }
  }

  /**
   *
   * @param webUrl
   * @param parentJob
   */
  def this(webUrl: WebUrl, parentJob: CrawlJob) {
    this(webUrl)
    assert(parent != null)
    this.parent = Some(parentJob)

    //Copy data from parent job.
    this.urlRegex = parentJob.urlRegex
    this.excludeUrl = parentJob.excludeUrl
    this.maxDepth = parentJob.maxDepth
    this.maxSubJob = parentJob.maxSubJob
    this.politenessDelay = parentJob.politenessDelay
    this.onlyCrawlInSameDomain = parentJob.onlyCrawlInSameDomain
  }

  /**
   * The depth of the job from the first job.
   */
  private var _depth: Int = 0

  def depth: Int = {
    if (!parent.isEmpty) {
      _depth = parent.get.depth + 1
    }
    _depth
  }

  protected var _errors = new ListBuffer[Logging]
  protected var _warns = new ListBuffer[Logging]
  protected var _infos = new ListBuffer[Logging]

  def errors = _errors

  //Check whether the job is error or not.
  def isError = !errors.isEmpty

  def infos = _infos

  def warns = _warns

  def info(msg: String, className: String, webUrl: WebUrl) {
    _infos += Logging(
      message = msg,
      className = className,
      url = Some(webUrl.url),
      logType = "info"
    )
  }

  def error(msg: String, className: String, webUrl: WebUrl) {
    _errors += Logging(
      message = msg,
      className = className,
      url = Some(webUrl.url),
      logType = "error"
    )
  }

  def error(msg: String, className: String, webUrl: WebUrl, ex: Throwable) {
    _errors += Logging(
      message = msg,
      className = className,
      exceptionClass = Some(ex.getClass.getName),
      url = Some(webUrl.url),
      logType = "error"
    )
  }

  /**
   * For debug information.
   * @param msg
   */
  def warn(msg: String, className: String) {
    _warns += Logging(
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
    _infos += Logging(
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
    _errors += Logging(
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
    _errors += Logging(
      message = msg,
      className = className,
      exceptionClass = Some(ex.getClass.getName),
      logType = "error"
    )
  }
}