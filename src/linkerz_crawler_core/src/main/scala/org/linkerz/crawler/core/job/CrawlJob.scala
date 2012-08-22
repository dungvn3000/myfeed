/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.job

import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.core.model.{WebPage, WebUrl}
import collection.mutable.ListBuffer
import scala.Some

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

  /**
   * The depth of the job from the first job.
   */
  private var _depth: Int = 0

  var code: CrawlJob.Status = CrawlJob.DONE

  /**
   * Max depth for a crawl job.
   */
  var maxDepth: Int = 1

  var onlyCrawlInSameDomain: Boolean = true

  /**
   * Only crawl the url match with this regex.
    */
  var urlRegex: List[String] = _

  /**
   * For those of url match with this regex will not be crawl.
   */
  var excludeUrl: List[String] = _

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

  def depth: Int = {
    if (!parent.isEmpty) {
      _depth = parent.get.depth + 1
    }
    _depth
  }
}

object CrawlJob extends Enumeration {
  type Status = Value
  val DONE, SKIP, ERROR = Value
}