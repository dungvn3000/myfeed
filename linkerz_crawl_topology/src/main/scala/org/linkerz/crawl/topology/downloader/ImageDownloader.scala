/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import com.ning.http.client.AsyncHttpClient
import org.linkerz.crawl.topology.job.CrawlJob
import org.apache.commons.lang.StringUtils
import net.coobird.thumbnailator.Thumbnails
import java.io.ByteArrayOutputStream
import org.apache.commons.httpclient.HttpStatus

/**
 * The Class ImageDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 8/19/12, 11:59 AM
 *
 */
class ImageDownloader(httpClient: AsyncHttpClient) extends Downloader {
  def download(crawlJob: CrawlJob) {
    crawlJob.result map {
      webPage => val imgUrl = webPage.featureImageUrl
      if (imgUrl.isDefined && StringUtils.isNotBlank(imgUrl.get)) {
        val response = httpClient.prepareGet(imgUrl.get).execute().get()
        if (response.getStatusCode == HttpStatus.SC_OK && response.getResponseBodyAsBytes.length > 0
          && response.getContentType.contains("image")) {
          val outputStream = new ByteArrayOutputStream()
          Thumbnails.of(response.getResponseBodyAsStream)
            .forceSize(160, 160).toOutputStream(outputStream)
          webPage.featureImage = Some(outputStream.toByteArray)
        }
      }
    }
  }

  def close() {
    httpClient.close()
  }
}
