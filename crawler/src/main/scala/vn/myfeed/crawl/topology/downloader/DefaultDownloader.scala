/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package vn.myfeed.crawl.topology.downloader

import grizzled.slf4j.Logging
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.HttpStatus
import org.apache.http.util.EntityUtils
import vn.myfeed.crawl.topology.downloader.handler.StrictlyRedirectStrategy

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class DefaultDownloader(httpClient: HttpClient = new DefaultHttpClient) extends Downloader with Logging {

  def download(url: String): Option[DownloadResult] = {
    val response = httpClient.execute(new HttpGet(url))
    info(s"Download ${response.getStatusLine.getStatusCode} : $url")

    if (response.getStatusLine.getStatusCode == HttpStatus.SC_OK) {
      var redirectUrl: Option[String] = None
      val handler = httpClient.asInstanceOf[DefaultHttpClient].getRedirectStrategy.asInstanceOf[StrictlyRedirectStrategy]
      if (handler != null) {
        redirectUrl = handler.lastRedirectedUri
        handler.lastRedirectedUri = None
      }

      val entity = response.getEntity
      val content = EntityUtils.toByteArray(entity)
      if (content != null && content.length > 0) {
        return Some(DownloadResult(
          url = url,
          redirectUrl = redirectUrl,
          content = content
        ))
      }
    }

    None
  }

}
