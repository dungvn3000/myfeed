/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import org.linkerz.crawl.topology.job.CrawlJob
import crawlercommons.fetcher.BaseFetcher
import grizzled.slf4j.Logging

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class NewsDownloader(htmlFetcher: BaseFetcher) extends Logging {

  def download(job: CrawlJob) {
    info("Download : " + job.url)
    job.result = htmlFetcher.get(job.url)
  }

}
