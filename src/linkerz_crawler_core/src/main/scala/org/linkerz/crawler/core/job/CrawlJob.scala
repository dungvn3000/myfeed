/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.job

import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.crawler.core.parser.ParserResult

/**
 * The Class CrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:55 AM
 *
 */

case class CrawlJob(var webUrl: WebUrl) extends Job {

  private var _parent: Option[CrawlJob] = None
  private var _result: Option[CrawlJobResult] = None

  /**
   * The depth of the job from the first job.
   */
  private var _depth: Int = 0

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

  def result_=(result: Option[CrawlJobResult]) {
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

case class CrawlJobResult(parserResult: ParserResult)
