package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{Parse, MetaFetch}
import java.util.UUID
import org.linkerz.crawl.topology.factory.DownloadFactory
import java.awt.image.BufferedImage
import collection.mutable.ListBuffer
import org.apache.http.HttpStatus
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream
import org.imgscalr.Scalr

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("sessionId", "event")) {

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, Parse(job)) => {
        if (job.result.exists(!_.isError)) {
          val scoreImage = new ListBuffer[(BufferedImage, Double)]
          val webPage = job.result.get
          val potentialImages = webPage.potentialImages

          var skip = false
          potentialImages.toList.sortBy(-_.length).foreach(imageUrl => if (!skip) {
            val downloader = DownloadFactory.createDownloader()

            try {
              val response = downloader.download(imageUrl)
              val entity = response.getEntity
              if (response.getStatusLine.getStatusCode == HttpStatus.SC_OK && entity.getContentLength > 0
                && entity.getContentType.getValue.contains("image")) {
                try {
                  val image = ImageIO.read(entity.getContent)
                  val score = image.getWidth + image.getHeight
                  scoreImage += image -> score

                  //Avoid download too much image, if the image score is 600, definitely it is good.
                  if (score >= 600) {
                    skip = true
                  }
                } catch {
                  case ex: Exception => {
                    job.error(ex.getMessage, getClass.getName, job.webUrl, ex)
                    _collector reportError ex
                  }
                }
              }
            } catch {
              case ex: Exception => {
                job.error(ex.getMessage, getClass.getName, job.webUrl, ex)
                _collector reportError ex
              }
            } finally {
              downloader.close()
            }
          })

          if (!scoreImage.isEmpty) {
            val bestImage = scoreImage.sortBy(-_._2).head._1
            val outputStream = new ByteArrayOutputStream
            try {
              val resizeImage = Scalr.resize(bestImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, 200, Scalr.OP_ANTIALIAS)
              ImageIO.write(resizeImage, "jpg", outputStream)
              outputStream.flush()
              webPage.featureImage = Some(outputStream.toByteArray)
            } catch {
              case ex: Exception => {
                job.error(ex.getMessage, getClass.getName, job.webUrl, ex)
                _collector reportError ex
              }
            } finally {
              outputStream.close()
            }
          }
        }

        tuple emit(sessionId, MetaFetch(job))
      }
    }
    tuple.ack()
  }
}
