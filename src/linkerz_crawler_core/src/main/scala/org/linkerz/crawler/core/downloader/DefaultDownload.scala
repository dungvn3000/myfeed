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

class DefaultDownload(httpClient: AsyncHttpClient) extends Downloader with Logging {

  def download(webUrl: WebUrl): DownloadResult = {
    info("Download: " + webUrl.url)
    val result = httpClient.prepareGet(webUrl.url).execute().get()
    new DownloadResult(webUrl, result.getResponseBodyAsBytes)
  }

}
