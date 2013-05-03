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
 * The Class FetchJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:55 AM
 *
 */

case class FetchJob(webUrl: WebUrl) {

  var parent: Option[FetchJob] = None

  var result: Option[WebPage] = None

  var responseCode: Int = _

  var feed: Feed = _

  var feeds: List[Feed] = _

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
   */
  def this(feed: Feed, feeds: List[Feed]) {
    this(feed.url)
    this.feed = feed
    this.feeds = feeds
  }

  /**
   *
   * @param webUrl
   * @param parentJob
   */
  def this(webUrl: WebUrl, parentJob: FetchJob) {
    this(webUrl)
    assert(parent != null)
    this.parent = Some(parentJob)
    this.feed = parentJob.feed
    this.feeds = parentJob.feeds
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