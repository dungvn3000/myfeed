/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.worker

import org.linkerz.job.queue.core.Worker
import org.linkerz.crawler.core.job.{CrawlJobResult, CrawlJob}
import org.linkerz.crawler.core.session.CrawlSession
import java.util.regex.Pattern
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.crawler.core.downloader.Downloader
import org.linkerz.crawler.core.parser.Parser

/**
 * The Class CrawlWorker.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 2:40 AM
 *
 */

class CrawlWorker(_id: Int, downloader: Downloader, parser: Parser) extends Worker[CrawlJob, CrawlSession] {

  private val filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
    + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$")

  val fetcher = new Fetcher(downloader, parser)

  def analyze(job: CrawlJob, session: CrawlSession) {
    val url = job.webUrl.url
    var parentDomainName = job.webUrl.domainName
    if (!job.parent.isEmpty) {
      parentDomainName = job.parent.get.webUrl.domainName
    }
    if (!filters.matcher(url).matches() && url.contains(parentDomainName)) {
      job.result = new Some(new CrawlJobResult(fetcher.fetch(job.webUrl)))
    } else {
      job.result = None
    }
  }

  def id = _id
}
