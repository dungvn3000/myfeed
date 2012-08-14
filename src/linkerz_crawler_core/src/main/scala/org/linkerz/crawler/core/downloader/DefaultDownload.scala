/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader

import grizzled.slf4j.Logging
import com.ning.http.client.{AsyncHttpClient, AsyncHttpClientConfig}
import org.linkerz.crawler.core.model.WebUrl
import edu.uci.ics.crawler4j.fetcher.PageFetcher

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class DefaultDownload(httpClient: AsyncHttpClient) extends Downloader with Logging {

  def download(webUrl: WebUrl): DownloadResult = {
    val result = httpClient.prepareGet(webUrl.url).execute().get()
    info("Download " + result.getStatusCode + " : " + webUrl.url)
    val downloadResult = new DownloadResult(webUrl, result.getResponseBodyAsBytes)
    downloadResult.responseCode = result.getStatusCode
    downloadResult
  }

}
