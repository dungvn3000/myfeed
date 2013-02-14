/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.job

import org.linkerz.model._
import collection.mutable.ListBuffer
import org.apache.http.HttpStatus
import org.linkerz.model.Feed
import org.linkerz.crawl.topology.model.WebUrl
import org.linkerz.model.Logging
import org.linkerz.crawl.topology.model.WebPage
import scala.Some

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

  var responseCode: Int = _

  var feed: Feed = _

  var feeds: List[Feed] = _

  var blackUrls: List[BlackUrl] = _

  /**
   * String url.
   * @param url
   */
  def this(url: String) {
    this(new WebUrl(url))
  }

  /**
   *
   * @param feed
   * @param feeds
   * @param blackUrls
   */
  def this(feed: Feed, feeds: List[Feed], blackUrls: List[BlackUrl]) {
    this(feed.url)

    if (!feed.urlRegex.isEmpty) {
      urlRegex = Some(feed.urlRegex)
    }

    if (!feed.excludeUrl.isEmpty) {
      excludeUrl = feed.excludeUrl
    }

    this.feed = feed
    this.feeds = feeds
    this.blackUrls = blackUrls
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

    this.feed = parentJob.feed
    this.feeds = parentJob.feeds
    this.blackUrls = parentJob.blackUrls
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
      url = Some(webUrl.toString),
      logType = LogType.Info.toString,
      category = LogCategory.Crawling.toString
    )
  }

  def error(msg: String, className: String) {
    errors += Logging(
      message = msg,
      className = className,
      url = Some(webUrl.toString),
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
      url = Some(webUrl.toString),
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
      url = Some(webUrl.toString),
      logType = LogType.Warn.toString,
      category = LogCategory.Crawling.toString
    )
  }
}