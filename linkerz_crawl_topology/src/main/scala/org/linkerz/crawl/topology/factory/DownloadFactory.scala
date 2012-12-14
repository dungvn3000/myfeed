/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import com.ning.http.client.{AsyncHttpClient, AsyncHttpClientConfig}
import org.linkerz.crawl.topology.downloader.{ImageDownloader, DefaultDownload}

/**
 * The Class DefaultDownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:41 PM
 *
 */

object DownloadFactory {

  val cf = new AsyncHttpClientConfig.Builder()
    .setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
    .setCompressionEnabled(true)
    .setAllowPoolingConnection(true)
    .setFollowRedirects(false)
    .setMaximumConnectionsPerHost(5)
    .setMaximumConnectionsTotal(50)
    .setRequestTimeoutInMs(1000 * 30)
    .setUseRawUrl(true)
    .build()

  def createDownloader() = new DefaultDownload(new AsyncHttpClient(cf))

  def createImageDownloader() = new ImageDownloader(new AsyncHttpClient(cf))

}