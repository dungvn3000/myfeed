/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader

import com.ning.http.client.AsyncHttpClient
import org.linkerz.crawler.core.job.CrawlJob
import org.apache.commons.lang.StringUtils
import org.apache.http.HttpStatus
import net.coobird.thumbnailator.Thumbnails
import java.io.ByteArrayOutputStream

/**
 * The Class ImageDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 8/19/12, 11:59 AM
 *
 */
class ImageDownloader(httpClient: AsyncHttpClient) extends Downloader {
  def download(crawlJob: CrawlJob) {
    val webPage = crawlJob.result.get
    val imgUrl = webPage.featureImageUrl

    if (StringUtils.isNotBlank(imgUrl)) {
      val response = httpClient.prepareGet(imgUrl).execute().get()
      if (response.getStatusCode == HttpStatus.SC_OK && response.getResponseBodyAsBytes.length > 0
        && response.getContentType.contains("image")) {
        val outputStream = new ByteArrayOutputStream()
        Thumbnails.of(response.getResponseBodyAsStream)
          .forceSize(160, 160).toOutputStream(outputStream)
        webPage.featureImage = outputStream.toByteArray
      }
    }
  }
}