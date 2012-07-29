/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader

import org.linkerz.crawler.core.model.WebUrl
import com.ning.http.client.{AsyncHttpClient, AsyncHttpClientConfig}
import grizzled.slf4j.Logging

/**
 * The Class Downloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:07 AM
 *
 */

class Downloader extends Logging {

  val cf = new AsyncHttpClientConfig.Builder()
    .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:10.0.2) Gecko/20100101 Firefox/10.0.2")
    .build()
  val asyncHttpClient = new AsyncHttpClient(cf)

  def download(webUrl: WebUrl): DownloadResult = {
    info("Download: " + webUrl.url)
    val result = asyncHttpClient.prepareGet(webUrl.url).execute().get()
    new DownloadResult(webUrl, result.getResponseBodyAsBytes)
  }

}
