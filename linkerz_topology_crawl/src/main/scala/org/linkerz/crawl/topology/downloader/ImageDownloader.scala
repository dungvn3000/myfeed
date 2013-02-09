/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient

/**
 * The Class ImageDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 8/19/12, 11:59 AM
 *
 */
class ImageDownloader(httpClient: HttpClient = new DefaultHttpClient()) extends Downloader {
//
//  def download(crawlJob: FeedJob) {
//
//    val webPage = new WebPage(new WebUrl(""))
//
//    val scoreImage = new ListBuffer[(BufferedImage, Double)]
//    val potentialImages = webPage.potentialImages
//
//    var skip = false
//    potentialImages.foreach(imageUrl => if (!skip) {
//      try {
//        val response = httpClient.execute(new HttpGet(imageUrl))
//        val entity = response.getEntity
//        if (response.getStatusLine.getStatusCode == HttpStatus.SC_OK) {
//          try {
//            val bytes = EntityUtils.toByteArray(entity)
//            val inputStream = new ByteArrayInputStream(bytes)
//            val image = ImageIO.read(inputStream)
//            val score = image.getWidth + image.getHeight
//            if (score >= 300) {
//              scoreImage += image -> score
//            }
//
//            //Avoid download too much images, if the image score is 600, definitely it is good.
//            if (score >= 600) {
//              skip = true
//            }
//          } catch {
//            case ex: Exception => {
////              crawlJob.error(ex.getMessage, getClass.getName, ex)
//            }
//          }
//        }
//      } catch {
//        case ex: Exception => {
////          crawlJob.error(ex.getMessage, getClass.getName, ex)
//        }
//      }
//    })
//
//    if (!scoreImage.isEmpty) {
//      val bestImage = scoreImage.sortBy(-_._2).head._1
//      val outputStream = new ByteArrayOutputStream
//      try {
//        var preferHeight = 500
//        if (bestImage.getHeight > 1000) {
//          preferHeight = bestImage.getHeight * 30 / 100
//        }
//        val resizeImage = Scalr.resize(bestImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, 300, preferHeight, Scalr.OP_ANTIALIAS)
//        ImageIO.write(resizeImage, "png", outputStream)
//        outputStream.flush()
//        webPage.featureImage = Some(outputStream.toByteArray)
//      } catch {
//        case ex: Exception => {
////          crawlJob.error(ex.getMessage, getClass.getName, ex)
//        }
//      } finally {
//        outputStream.close()
//      }
//    }
//
//  }


  def download(url: String) = throw new UnsupportedOperationException()

  def close() {
    httpClient.getConnectionManager.shutdown()
  }
}
