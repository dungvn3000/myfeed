package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.job.FetchJob
import com.github.sonic.parser.{ArticleParser, LinksParser}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import org.linkerz.model.Feed
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

  def parse(crawlJob: FetchJob) {
    crawlJob.result.map(webPage => {
      info("Parse: " + webPage.urlAsString)
      if (webPage.content != null) {
        val inputStream = new ByteArrayInputStream(webPage.content)
        val doc = Jsoup.parse(inputStream, null, webPage.urlAsString)

        webPage.webUrls = linksParser.parse(doc).map(new WebUrl(_))

      }
    })
  }
}
