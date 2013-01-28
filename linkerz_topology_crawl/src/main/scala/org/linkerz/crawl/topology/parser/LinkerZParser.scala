package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.parser.{ArticleParser, LinksParser}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import collection.JavaConversions._
import org.apache.commons.lang.StringUtils
import gumi.builders.UrlBuilder
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import collection.mutable
import org.apache.commons.validator.routines.UrlValidator
import org.linkerz.model.Feed
import org.linkerz.core.matcher.SimpleRegexMatcher._
import org.linkerz.parser.model.WebUrl

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
    crawlJob.result.map(result => {
      val webUrl = result.webUrl
      info("Parse: " + webUrl.toString)
      if (result.content != null) {
        val inputStream = new ByteArrayInputStream(result.content)
        val doc = Jsoup.parse(inputStream, result.contentEncoding, result.webUrl.baseUrl)

        result.webUrls = linksParser.parse(doc)

        feeds.find(feed => matcher(webUrl.toString, feed.urlRegex)).map(feed => {
          result.article = articleParser.parse(doc, feed.contentSelection, feed.removeSelections)
        })

        result.feedId = crawlJob.feedId
      }
    })
  }
}
