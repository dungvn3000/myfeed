package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.parser.{ArticleParser, LinksParser}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import collection.JavaConversions._
import org.linkerz.crawl.topology.model.WebUrl
import org.apache.commons.lang.StringUtils
import gumi.builders.UrlBuilder
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import collection.mutable
import org.apache.commons.validator.routines.UrlValidator
import org.linkerz.model.NewFeed
import org.linkerz.core.matcher.SimpleRegexMatcher._

/**
 * The this class using two parser LinksParse and ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:15 PM
 *
 */
class LinkerZParser(feeds: List[NewFeed]) extends Parser {

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
        links.foreach(webPage.webUrls += new WebUrl(_))

        feeds.find(feed => matcher(webUrl.url, feed.urlRegex)).map(feed => {
          articleParser.parse(doc, feed.contentSelection).map(article => {
            webPage.title = article.title
            if (StringUtils.isNotBlank(article.description())) {
              webPage.description = Some(article.description())
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

            webPage.tags = crawlJob.tags

            webPage.isArticle = true
          })
        })
      }
    })
  }
}
