/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.worker

import org.linkerz.job.queue.core.Worker
import org.linkerz.crawler.core.job.{CrawlJobResult, CrawlJob}
import org.linkerz.crawler.core.session.CrawlSession
import org.linkerz.crawler.core.downloader.Downloader
import org.linkerz.crawler.core.parser.Parser
import java.util.regex.Pattern

/**
 * The Class CrawlWorker.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 2:40 AM
 *
 */

class CrawlWorker(_id: Int) extends Worker[CrawlJob, CrawlSession] {

  private val filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
    + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$")

  val downloader = new Downloader
  val parser = new Parser

  def analyze(job: CrawlJob, session: CrawlSession) {
    if (!filters.matcher(job.webUrl.url.toLowerCase).matches()) {
      val downloadResult = downloader.download(job.webUrl)
      val parserResult = parser.parse(downloadResult)
      job.result = new Some(CrawlJobResult(parserResult))
    } else {
      job.result = None
    }
  }

  def id = _id
}
