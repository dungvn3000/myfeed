/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.factory

import com.ning.http.client.{AsyncHttpProviderConfig, AsyncHttpClient, AsyncHttpClientConfig}
import org.linkerz.crawler.core.downloader.DefaultDownload
import com.ning.http.client.providers.apache.{ApacheAsyncHttpProviderConfig, ApacheAsyncHttpProvider}

/**
 * The Class DefaultDownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:41 PM
 *
 */

class DefaultDownloadFactory extends DownloadFactory {

  val cf = new AsyncHttpClientConfig.Builder()
    .setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
    .setCompressionEnabled(true)
    .setAllowPoolingConnection(true)
    .setFollowRedirects(true)
    .setMaximumNumberOfRedirects(100)
    .setMaximumConnectionsPerHost(5)
    .build()

  def createDownloader() = {
    new DefaultDownload(new AsyncHttpClient(cf))
  }
}
