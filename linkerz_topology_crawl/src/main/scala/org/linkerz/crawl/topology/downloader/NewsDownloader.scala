/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import crawlercommons.fetcher.BaseFetcher
import grizzled.slf4j.Logging
import com.sun.syndication.feed.synd.SyndEntry

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class NewsDownloader(htmlFetcher: BaseFetcher) extends Logging {

  def download(entry: SyndEntry) = {
    info("Download : " + entry.getLink)
    htmlFetcher.get(entry.getLink)
  }

}
