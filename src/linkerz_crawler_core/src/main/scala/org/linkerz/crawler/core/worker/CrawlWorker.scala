/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.worker

import org.linkerz.job.queue.core.Worker
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.session.CrawlSession
import org.linkerz.crawler.core.downloader.Downloader
import org.linkerz.crawler.core.parser.Parser
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class CrawlWorker.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 2:40 AM
 *
 */

class CrawlWorker extends Worker[CrawlJob, CrawlSession] {
  val downloader = new Downloader
  val parser = new Parser

  def analyze(job: CrawlJob, session: CrawlSession) {
    val downloadResult = downloader.download(job.webUrl)
    val parserResult = parser.parse(downloadResult)

    if (!parserResult.webUrls.isEmpty) {
      job.result = Some[List[WebUrl]](parserResult.webUrls)
    }
  }
}
