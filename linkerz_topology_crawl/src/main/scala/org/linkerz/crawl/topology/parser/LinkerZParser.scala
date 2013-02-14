package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.parser.{ArticleParser, LinksParser}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import org.apache.commons.lang.StringUtils
import gumi.builders.UrlBuilder
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import collection.mutable
import org.apache.commons.validator.routines.UrlValidator
import org.linkerz.model.Feed
import org.linkerz.core.matcher.SimpleRegexMatcher._
import org.linkerz.crawl.topology.model.WebUrl

/**
 * The this class using two parser LinksParse and ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:15 PM
 *
 */
class LinkerZParser(feeds: List[Feed]) extends Parser {

  val linksParser = new LinksParser
  val articleParser = new ArticleParser

  def parse(crawlJob: CrawlJob) {
    crawlJob.result.map(webPage => {
      info("Parse: " + webPage.urlAsString)
      if (webPage.content != null) {
        val inputStream = new ByteArrayInputStream(webPage.content)
        val doc = Jsoup.parse(inputStream, null, webPage.urlAsString)

        webPage.webUrls = linksParser.parse(doc).map(new WebUrl(_))

        feeds.find(feed => matcher(webPage.urlAsString.toString, feed.urlRegex)).map(feed => {
          articleParser.parse(doc, feed.contentSelection, feed.removeSelections).map(article => {
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

            if (crawlJob.feed != null) {
              webPage.feedId = crawlJob.feed._id
            }

            webPage.isArticle = true
          })
        })
      }
    })
  }
}
