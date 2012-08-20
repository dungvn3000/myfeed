/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.fetcher

import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.factory.{ParserFactory, DownloadFactory}

/**
 * The Class Fetcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/1/12, 1:52 AM
 *
 */

class Fetcher(downloadFactory: DownloadFactory, parserFactory: ParserFactory) {

  val downloader = downloadFactory.createDownloader()
  val imageDownloader = downloadFactory.createImageDownloader()
  val parser = parserFactory.createParser()

  /**
   * Fetch a url
   * @param crawlJob
   */
  def fetch(crawlJob: CrawlJob) {
    downloader.download(crawlJob)
    if (!crawlJob.result.isEmpty && !crawlJob.result.get.isError) {
      //Only parse when the response code is ok
      parser.parse(crawlJob)

      //Download the feature image of the website if it's exits.
      imageDownloader.download(crawlJob)
    }
  }

}
