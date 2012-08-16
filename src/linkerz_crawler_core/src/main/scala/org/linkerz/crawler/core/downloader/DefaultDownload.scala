/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader

import grizzled.slf4j.Logging
import com.ning.http.client.AsyncHttpClient
import org.linkerz.crawler.core.model.WebUrl
import org.apache.http.HttpStatus
import org.apache.commons.lang.StringUtils
import org.apache.http.client.utils.URIUtils
import java.net.URI

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class DefaultDownload(httpClient: AsyncHttpClient) extends Downloader with Logging {

  def download(webUrl: WebUrl): DownloadResult = {
    val response = httpClient.prepareGet(webUrl.url).execute().get()
    info("Download " + response.getStatusCode + " : " + webUrl.url)

    if (response.getStatusCode == HttpStatus.SC_MOVED_PERMANENTLY
      || response.getStatusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
      val location = response.getHeader("Location")
      if (StringUtils.isNotBlank(location)) {
        webUrl.movedToUrl = location
      }
    }

    val downloadResult = new DownloadResult(webUrl, response.getResponseBodyAsBytes)
    downloadResult.responseCode = response.getStatusCode
    downloadResult
  }

}
