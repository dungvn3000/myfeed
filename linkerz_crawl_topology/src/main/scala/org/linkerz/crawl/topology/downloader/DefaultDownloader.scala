/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.model.WebPage
import org.apache.http.HttpStatus
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils
import org.apache.http.client.entity.GzipDecompressingEntity

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class DefaultDownloader(httpClient: HttpClient = new DefaultHttpClient) extends Downloader {

  def download(crawlJob: CrawlJob) {
    val webUrl = crawlJob.webUrl
    val webPage = new WebPage

    val response = httpClient.execute(new HttpGet(webUrl.url))
    info("Download " + response.getStatusLine.getStatusCode + " : " + webUrl.url)

    if (response.getStatusLine.getStatusCode == HttpStatus.SC_OK) {
      var entity = response.getEntity
      if (entity.getContentEncoding != null) {
        if (entity.getContentEncoding.toString.contains("gzip")) {
          entity = new GzipDecompressingEntity(entity)
        }
      }

      webPage.content = EntityUtils.toByteArray(entity)
      webPage.contentType = response.getEntity.getContentType.getValue
      if (response.getEntity.getContentEncoding != null) {
        webPage.contentEncoding = response.getEntity.getContentEncoding.getValue
      }
    }

    webPage.webUrl = webUrl
    webPage.responseCode = response.getStatusLine.getStatusCode

    crawlJob.result = Some(webPage)
  }

  def download(url: String) = httpClient.execute(new HttpGet(url))

  def close() {
    httpClient.getConnectionManager.shutdown()
  }
}
