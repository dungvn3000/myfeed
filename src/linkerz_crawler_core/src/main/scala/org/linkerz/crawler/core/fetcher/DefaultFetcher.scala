/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.fetcher

import org.linkerz.crawler.core.factory.{ParserFactory, DownloadFactory}
import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class DefaultFetcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/20/12, 10:16 PM
 *
 */
class DefaultFetcher(downloadFactory: DownloadFactory, parserFactory: ParserFactory) extends Fetcher {

  val downloader = downloadFactory.createDownloader()
  val parser = parserFactory.createParser()

  override def fetch(crawlJob: CrawlJob) {
    downloader.download(crawlJob)
    if (!crawlJob.result.isEmpty && !crawlJob.result.get.isError) {
      //Only parse when the response code is ok
      parser.parse(crawlJob)
    }
  }

}
