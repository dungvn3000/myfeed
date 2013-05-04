package org.linkerz.crawl.topology.downloader

import crawlercommons.fetcher.BaseFetcher
import grizzled.slf4j.Logging
import org.linkerz.model.Feed

/**
 * The Class RssDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 5/4/13 11:47 PM
 *
 */
class RssDownloader(htmlFetcher: BaseFetcher) extends Logging {
  def download(feed: Feed) = {
    info("Download : " + feed.url)
    htmlFetcher.get(feed.url)
  }
}
