/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.fetcher

import org.linkerz.crawler.core.fetcher.DefaultFetcher
import org.linkerz.crawler.core.factory.{ParserFactory, DownloadFactory}
import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class NewFetcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/20/12, 10:23 PM
 *
 */
class NewFetcher(downloadFactory: DownloadFactory, parserFactory: ParserFactory) extends DefaultFetcher(downloadFactory, parserFactory) {

  val imageDownloader = downloadFactory.createImageDownloader()

  override def parse(crawlJob: CrawlJob) {
    super.parse(crawlJob)

    //After parsing, download the image for the new.
    imageDownloader.download(crawlJob)
  }
}
