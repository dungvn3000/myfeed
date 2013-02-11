package org.linkerz.crawl.topology.parser

import org.linkerz.parser.ArticleParser
import org.linkerz.crawl.topology.model.WebPage
import grizzled.slf4j.Logging
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import org.apache.commons.lang.StringUtils
import org.apache.commons.validator.routines.UrlValidator
import gumi.builders.UrlBuilder
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import collection.mutable
import com.sun.syndication.feed.synd.SyndEntry

/**
 * The this class using two parser LinksParse and ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:15 PM
 *
 */
class WebPageParser extends Logging {

  val articleParser = new ArticleParser

  def parse(webPage: WebPage, entry: Option[SyndEntry] = None) {
    info("Parse: " + webPage.urlAsString)
    if (webPage.content != null) {
      val inputStream = new ByteArrayInputStream(webPage.content)
      val doc = Jsoup.parse(inputStream, webPage.contentEncoding, webPage.urlAsString)

      val article = if (entry.isDefined && entry.get.getDescription != null) {
        articleParser.parse(doc, entry.get.getTitle, entry.get.getDescription.getValue)
      } else {
        articleParser.parse(doc)
      }

      webPage.title = article.title
      if (StringUtils.isNotBlank(article.description)) {
        webPage.description = Some(article.description)
      }
      if (StringUtils.isNotBlank(article.text)) {
        webPage.text = Some(article.text)
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
      webPage.potentialImages = potentialImages.toList

      webPage.isArticle = true
    }
  }
}
