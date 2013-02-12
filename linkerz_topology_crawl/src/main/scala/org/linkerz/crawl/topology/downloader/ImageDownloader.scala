/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import org.linkerz.crawl.topology.job.CrawlJob
import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import collection.mutable.ListBuffer
import java.awt.image.BufferedImage
import org.apache.http.HttpStatus
import javax.imageio.ImageIO
import org.apache.http.client.methods.HttpGet
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import org.apache.http.util.EntityUtils
import net.coobird.thumbnailator.Thumbnails

/**
 * The Class ImageDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 8/19/12, 11:59 AM
 *
 */
class ImageDownloader(httpClient: HttpClient = new DefaultHttpClient()) extends Downloader {

  def download(crawlJob: CrawlJob) {
    crawlJob.result.map(webPage => {
      val scoreImage = new ListBuffer[(BufferedImage, Double)]
      val potentialImages = webPage.potentialImages

      var skip = false
      potentialImages.foreach(imageUrl => if (!skip) {
        try {
          val response = httpClient.execute(new HttpGet(imageUrl))
          val entity = response.getEntity
          if (response.getStatusLine.getStatusCode == HttpStatus.SC_OK) {
            try {
              val bytes = EntityUtils.toByteArray(entity)
              val inputStream = new ByteArrayInputStream(bytes)
              val image = ImageIO.read(inputStream)
              val score = image.getWidth + image.getHeight
              if (score >= 300) {
                scoreImage += image -> score
              }
              inputStream.close()

              //Avoid download too much images, if the image score is 600, definitely it is good.
              if (score >= 600) {
                skip = true
              }

            } catch {
              case ex: Exception => {
                crawlJob.error(ex.getMessage, getClass.getName, ex)
              }
            }
          }
        } catch {
          case ex: Exception => {
            crawlJob.error(ex.getMessage, getClass.getName, ex)
          }
        }
      })

      if (!scoreImage.isEmpty) {
        val bestImage = scoreImage.sortBy(-_._2).head._1
        val outputStream = new ByteArrayOutputStream
        try {
          var preferHeight = 500
          if (bestImage.getHeight > 1000) {
            preferHeight = bestImage.getHeight * 30 / 100
          }

          Thumbnails.of(bestImage).size(300, preferHeight).outputFormat("jpeg").toOutputStream(outputStream)
          webPage.featureImage = Some(outputStream.toByteArray)
        } catch {
          case ex: Exception => {
            crawlJob.error(ex.getMessage, getClass.getName, ex)
          }
        } finally {
          outputStream.close()
        }
      }
    })

  }


  def download(url: String) = throw new UnsupportedOperationException()

  def close() {
    httpClient.getConnectionManager.shutdown()
  }
}
