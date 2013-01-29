/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.job

import org.linkerz.crawl.topology.model.{WebPage, WebUrl}
import scala.Some
import org.linkerz.model.{LogCategory, LogType, Feed, Logging}
import collection.mutable.ListBuffer
import org.bson.types.ObjectId
import org.apache.http.HttpStatus

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
  val filterPattern = ".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|exe|msi|jar|flv|doc|docx|xls|xlsx|ppt|pptx|rm|smil|wmv|swf|wma|zip|rar|gz))$".r.pattern

  /**
   * Only crawl the url match with this regex.
   */
  var urlRegex: Option[String] = None

  /**
   * For those of url match with this regex will not be crawl.
   */
  var excludeUrl: List[String] = Nil

  /**
   * Max depth for a crawl job, default is unlimited.
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
   * The result of this job will has this attribute.
   */
  var feedId: ObjectId = _

  var responseCode: Int = _

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
  def this(newFeed: Feed) {
    this(newFeed.url)

    if (!newFeed.urlRegex.isEmpty) {
      urlRegex = Some(newFeed.urlRegex)
    }

    if (!newFeed.excludeUrl.isEmpty) {
      excludeUrl = newFeed.excludeUrl
    }

    feedId = newFeed._id
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
    this.feedId = parentJob.feedId
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

  val errors = new ListBuffer[Logging]
  val warns = new ListBuffer[Logging]
  val infos = new ListBuffer[Logging]

  //Check whether the job is error or not.
  def isError = !errors.isEmpty || responseCode != HttpStatus.SC_OK

  def info(msg: String, className: String) {
    infos += Logging(
      message = msg,
      className = className,
      url = Some(webUrl.url),
      logType = LogType.Info.toString,
      category = LogCategory.Crawling.toString
    )
  }

  def error(msg: String, className: String) {
    errors += Logging(
      message = msg,
      className = className,
      url = Some(webUrl.url),
      logType = LogType.Error.toString,
      category = LogCategory.Crawling.toString
    )
  }

  def error(msg: String, className: String, ex: Throwable) {
    errors += Logging(
      message = msg,
      className = className,
      exceptionClass = Some(ex.getClass.getName),
      stackTrace = Some(ex.getStackTraceString),
      url = Some(webUrl.url),
      logType = LogType.Error.toString,
      category = LogCategory.Crawling.toString
    )
  }

  /**
   * For debug information.
   * @param msg
   */
  def warn(msg: String, className: String) {
    warns += Logging(
      message = msg,
      className = className,
      url = Some(webUrl.url),
      logType = LogType.Warn.toString,
      category = LogCategory.Crawling.toString
    )
  }
}