/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import org.linkerz.crawl.topology.job.CrawlJob
import collection.mutable.ListBuffer
import java.awt.image.BufferedImage
import org.apache.http.HttpStatus
import javax.imageio.ImageIO
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import net.coobird.thumbnailator.Thumbnails
import crawlercommons.fetcher.BaseFetcher
import org.linkerz.model.Image

/**
 * The Class ImageDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 8/19/12, 11:59 AM
 *
 */
class ImageDownloader(httpFetcher: BaseFetcher) extends Downloader {

  def download(crawlJob: CrawlJob) {
    crawlJob.result.map(webPage => {
      val scoreImage = new ListBuffer[(BufferedImage, Double)]
      val potentialImages = webPage.potentialImages

      var skip = false
      potentialImages.foreach(imageUrl => if (!skip) {
        try {
          val result = httpFetcher.get(imageUrl)
          if (result.getStatusCode == HttpStatus.SC_OK && result.getContentLength > 0) {
            try {
              val bytes = result.getContent
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

          val bytes = outputStream.toByteArray
          if (bytes.length > 0) {
            webPage.featureImage = Some(Image(original = bytes))
          }
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
}
