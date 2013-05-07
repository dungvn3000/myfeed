/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import grizzled.slf4j.Logging
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.entity.GzipDecompressingEntity
import org.apache.http.HttpStatus
import org.apache.http.util.EntityUtils

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
      var entity = response.getEntity

      if (entity.getContentEncoding != null && entity.getContentEncoding.toString.contains("gzip")) {
        entity = new GzipDecompressingEntity(entity)
      }
      val content = EntityUtils.toByteArray(entity)
      if(content != null && content.length > 0) {
        return Some(DownloadResult(
          url = url,
          content = content
        ))
      }
    }

    None
  }

}
