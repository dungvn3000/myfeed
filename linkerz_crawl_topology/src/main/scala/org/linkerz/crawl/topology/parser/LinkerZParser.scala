package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.parser.{ArticleParser, LinksParser}
import java.io.{ByteArrayOutputStream, ByteArrayInputStream}
import org.jsoup.Jsoup
import collection.JavaConversions._
import org.linkerz.crawl.topology.model.WebUrl
import org.apache.commons.lang.StringUtils
import gumi.builders.UrlBuilder
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import org.linkerz.crawl.topology.factory.DownloadFactory
import collection.mutable
import org.apache.http.HttpStatus
import scala.util.control.Breaks._
import collection.mutable.ListBuffer
import org.imgscalr.Scalr
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import org.apache.commons.validator.routines.UrlValidator

/**
 * The this class using two parser LinksParse and ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:15 PM
 *
 */
class LinkerZParser extends Parser {

  val linksParser = new LinksParser
  val articleParser = new ArticleParser

  def parse(crawlJob: CrawlJob) {
    crawlJob.result.map(webPage => {
      val webUrl = webPage.webUrl
      info("Parse: " + webUrl.url)
      if (webPage.content != null) {
        val inputStream = new ByteArrayInputStream(webPage.content)
        val doc = Jsoup.parse(inputStream, webPage.contentEncoding, webPage.webUrl.url)

        val links = linksParser.parse(doc)
        val article = articleParser.parse(doc)

        webPage.webUrls = links.toList.map(new WebUrl(_))
        webPage.title = article.title
        if (StringUtils.isNotBlank(article.fullText)) {
          webPage.text = Some(article.fullText)
        }
        if (StringUtils.isNotBlank(article.text)) {
          webPage.description = Some(article.text)
        }
        val potentialImages = new mutable.HashSet[String]
        val urlValidator = new UrlValidator(Array("http", "https"))
        article.images.foreach(image => {
          val imgSrc = UrlBuilder.fromString(image.src).toString
          if (StringUtils.isNotBlank(imgSrc)) {
            val url = URLCanonicalizer.getCanonicalURL(imgSrc, webPage.webUrl.baseUrl)
            if (StringUtils.isNotBlank(url) && urlValidator.isValid(url)) {
              potentialImages += url
            }
          }
        })

        val scoreImage = new ListBuffer[(BufferedImage, Double)]

        breakable {
          potentialImages.toList.sortBy(-_.length).foreach(imageUrl => {
            val downloader = DownloadFactory.createDownloader()
            val response = downloader.download(imageUrl)
            val entity = response.getEntity
            if (response.getStatusLine.getStatusCode == HttpStatus.SC_OK && entity.getContentLength > 0
              && entity.getContentType.getValue.contains("image")) {
              val image = ImageIO.read(entity.getContent)
              val score = image.getWidth + image.getHeight
              scoreImage += image -> score

              //Avoid download too much image, if the image score is 600, definitely it is good.
              if (score >= 600) {
                downloader.close()
                break()
              }
            }
            downloader.close()
          })
        }

        if (!scoreImage.isEmpty) {
          val bestImage = scoreImage.sortBy(-_._2).head._1
          val outputStream = new ByteArrayOutputStream
          val resizeImage = Scalr.resize(bestImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, 200, Scalr.OP_ANTIALIAS)
          ImageIO.write(resizeImage, "jpg", outputStream)
          outputStream.flush()
          webPage.featureImage = Some(outputStream.toByteArray)
          outputStream.close()
        }

      }
    })
  }
}
