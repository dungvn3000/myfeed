/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.factory

import com.ning.http.client.{AsyncHttpClient, AsyncHttpClientConfig}
import org.linkerz.crawler.core.downloader.DefaultDownload

/**
 * The Class DefaultDownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:41 PM
 *
 */

class DefaultDownloadFactory extends DownloadFactory {

  val cf = new AsyncHttpClientConfig.Builder()
    .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:10.0.2) Gecko/20100101 Firefox/10.0.2")
    .setCompressionEnabled(true)
    .setAllowPoolingConnection(true)
    .setFollowRedirects(false)
    .build()

  def createDownloader() = {
    new DefaultDownload(new AsyncHttpClient(cf))
  }
}
