/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.crawl.topology.downloader.{ImageDownloader, DefaultDownloader}
import crawlercommons.fetcher.http.SimpleHttpFetcher

/**
 * The Class DefaultDownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:41 PM
 *
 */

object DownloadFactory {

  def createDownloader() = {
    new DefaultDownloader(new SimpleHttpFetcher(100, new LinkerZUserAgent))
  }

  def createImageDownloader() = {
    val fetcher = new SimpleHttpFetcher(100, new LinkerZUserAgent)
    //set maximum file size is 1mb
    fetcher.setMaxContentSize("image/jpeg", 1024 * 1024)
    new ImageDownloader(fetcher)
  }

}