/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.crawl.topology.downloader.{RssDownloader, NewsDownloader}
import crawlercommons.fetcher.http.SimpleHttpFetcher

/**
 * The Class DefaultDownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:41 PM
 *
 */

object DownloadFactory {

  def createNewsDownloader() = {
    new NewsDownloader(new SimpleHttpFetcher(100, new LinkerZUserAgent))
  }

  def createRssDownloader() = {
    new RssDownloader(new SimpleHttpFetcher(100, new LinkerZUserAgent))
  }

}