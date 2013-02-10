/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.linkerz.crawl.topology.model.{WebPage, WebUrl}
import grizzled.slf4j.Logging
import org.apache.http.HttpStatus
import org.apache.http.entity.ContentType
import org.apache.http.util.EntityUtils
import org.apache.http.client.entity.GzipDecompressingEntity
import org.linkerz.crawl.topology.factory.StrictlyRedirectStrategy
import org.linkerz.core.string.RichString._

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class WebPageDownloader(httpClient: HttpClient = new DefaultHttpClient) extends Logging {

  def download(webUrl: WebUrl): Option[WebPage] = {
    val response = httpClient.execute(new HttpGet(webUrl.toString))
    if (response.getStatusLine != null) info("Download " + response.getStatusLine.getStatusCode + " : " + webUrl)

    if (response.getStatusLine.getStatusCode == HttpStatus.SC_OK) {
      var entity = response.getEntity
      if (entity.getContentEncoding != null) {
        if (entity.getContentEncoding.toString.contains("gzip")) {
          entity = new GzipDecompressingEntity(entity)
        }
      }

      var webPage = WebPage(webUrl)
      if (httpClient.isInstanceOf[DefaultHttpClient]) {
        val redirectHandler = httpClient.asInstanceOf[DefaultHttpClient].getRedirectStrategy
        if (redirectHandler.isInstanceOf[StrictlyRedirectStrategy]) {
          val redirectUrl = redirectHandler.asInstanceOf[StrictlyRedirectStrategy].lastRedirectedUri

          if (redirectUrl.isNotBlank) {
            webPage = WebPage(webUrl.copy(url = redirectUrl))
          }
        }
      }

      webPage.content = EntityUtils.toByteArray(entity)

      if (response.getEntity.getContentType != null) {
        webPage.contentType = response.getEntity.getContentType.getValue
      }

      if (ContentType.getOrDefault(entity).getCharset != null) {
        webPage.contentEncoding = ContentType.getOrDefault(entity).getCharset.name()
      }

      return Some(webPage)
    }

    None
  }

}
