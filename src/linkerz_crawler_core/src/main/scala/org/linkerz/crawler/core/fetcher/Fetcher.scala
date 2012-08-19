/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.fetcher

import org.linkerz.crawler.core.downloader.Downloader
import org.linkerz.crawler.core.parser.{ParserResult, Parser}
import org.linkerz.crawler.core.model.{WebPage, WebUrl}
import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class Fetcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/1/12, 1:52 AM
 *
 */

class Fetcher(downloader: Downloader, imageDownloader: Downloader , parser: Parser) {

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
