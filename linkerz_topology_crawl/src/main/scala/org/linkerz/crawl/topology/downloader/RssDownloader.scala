package org.linkerz.crawl.topology.downloader

import crawlercommons.fetcher.BaseFetcher
import org.linkerz.crawl.topology.job.FetchJob
import grizzled.slf4j.Logging

/**
 * The Class RssDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 5/4/13 11:47 PM
 *
 */
class RssDownloader(htmlFetcher: BaseFetcher) extends Logging {
  def download(feedJob: FetchJob) {
    info("Download : " + feedJob.url)
    feedJob.result = htmlFetcher.get(feedJob.url)
  }
}
