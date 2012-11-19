/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core.job

import org.linkerz.job.queue.core.{JobStatus, Job}
import org.linkerz.crawler.core.model.{WebPage, WebUrl}
import scala.Some
import java.util.regex.Pattern
import org.linkerz.model.Logging

/**
 * The Class CrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:55 AM
 *
 */

case class CrawlJob(webUrl: WebUrl) extends Job {

  private var _parent: Option[CrawlJob] = None
  private var _result: Option[WebPage] = None

  var code: CrawlJob.Status = CrawlJob.DONE

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
   * String url.
   * @param url
   */
  def this(url: String) {
    this(new WebUrl(url))
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
  }

  def result = {
    _result
  }

  def result_=(result: Option[WebPage]) {
    _result = result
  }

  override def parent = _parent

  def parent_=(parent: Option[CrawlJob]) {
    _parent = parent
  }

  def info(msg: String, className: String, webUrl: WebUrl) {
    _infos += Logging(
      message = msg,
      className = className,
      url = Some(webUrl.url),
      logType = "info"
    )
  }

  def error(msg: String, className: String, webUrl: WebUrl) {
    status = JobStatus.ERROR
    _errors += Logging(
      message = msg,
      className = className,
      url = Some(webUrl.url),
      logType = "error"
    )
  }

  def error(msg: String, className: String, webUrl: WebUrl, ex: Throwable) {
    status = JobStatus.ERROR
    _errors += Logging(
      message = msg,
      className = className,
      exceptionClass = Some(ex.getClass.getName),
      url = Some(webUrl.url),
      logType = "error"
    )
  }
}

object CrawlJob extends Enumeration {
  type Status = Value
  val DONE, SKIP, ERROR = Value
}