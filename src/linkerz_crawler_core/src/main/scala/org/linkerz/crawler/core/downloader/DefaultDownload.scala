/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader

import grizzled.slf4j.Logging
import com.ning.http.client.{AsyncHttpClient, AsyncHttpClientConfig}
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class DefaultDownload extends Downloader with Logging {

  val cf = new AsyncHttpClientConfig.Builder()
    .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:10.0.2) Gecko/20100101 Firefox/10.0.2")
    .setCompressionEnabled(true)
    .setAllowPoolingConnection(true)
    .setFollowRedirects(false)
    .build()
  val asyncHttpClient = new AsyncHttpClient(cf)

  def download(webUrl: WebUrl): DownloadResult = {
    info("Download: " + webUrl.url)
    val result = asyncHttpClient.prepareGet(webUrl.url).execute().get()
    new DownloadResult(webUrl, result.getResponseBodyAsBytes)
  }

}
