package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.parser.{ArticleParser, LinksParser}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import collection.JavaConversions._
import org.linkerz.crawl.topology.model.WebUrl
import org.apache.commons.lang.StringUtils

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

        webPage.webUrls  = links.toList.map(new WebUrl(_))
        webPage.title = article.title
        if (StringUtils.isNotBlank(article.fullText)) {
          webPage.text = Some(article.fullText)
        }
        if (StringUtils.isNotBlank(article.text)) {
          webPage.description = Some(article.text)
        }
      }
    })
  }
}
