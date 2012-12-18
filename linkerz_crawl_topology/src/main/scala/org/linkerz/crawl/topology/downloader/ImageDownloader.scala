/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import org.linkerz.crawl.topology.job.CrawlJob
import org.apache.commons.lang.StringUtils
import net.coobird.thumbnailator.Thumbnails
import java.io.ByteArrayOutputStream
import net.coobird.thumbnailator.resizers.configurations.Antialiasing
import org.apache.http.HttpStatus
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet

/**
 * The Class ImageDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 8/19/12, 11:59 AM
 *
 */
class ImageDownloader(httpClient: HttpClient = new DefaultHttpClient()) extends Downloader {
  def download(crawlJob: CrawlJob) {
    crawlJob.result map {
      webPage => val imgUrl = webPage.featureImageUrl
      if (imgUrl.isDefined && StringUtils.isNotBlank(imgUrl.get)) {
        val response = httpClient.execute(new HttpGet(imgUrl.get))
        val entity = response.getEntity
        if (response.getStatusLine.getStatusCode == HttpStatus.SC_OK && entity.getContentLength > 0
          && entity.getContentType.getValue.contains("image")) {
          val outputStream = new ByteArrayOutputStream()
          Thumbnails.of(entity.getContent)
            .size(300, 300).keepAspectRatio(true).antialiasing(Antialiasing.ON).toOutputStream(outputStream)
          webPage.featureImage = Some(outputStream.toByteArray)
        }
      }
    }
  }

  def close() {
    httpClient.getConnectionManager.shutdown()
  }
}
