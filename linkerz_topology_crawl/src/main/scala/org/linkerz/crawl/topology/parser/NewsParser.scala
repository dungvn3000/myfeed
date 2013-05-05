package org.linkerz.crawl.topology.parser

import com.github.sonic.parser.ArticleParser
import crawlercommons.fetcher.FetchedResult
import org.apache.http.HttpStatus
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import com.github.sonic.parser.model.Article

/**
 * The this class using two parser LinksParse and ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:15 PM
 *
 */
class NewsParser {

  val articleParser = new ArticleParser

  def parse(result: FetchedResult): Option[Article] = {
    if (result.getStatusCode == HttpStatus.SC_OK && result.getContentLength > 0) {
      val input = new ByteArrayInputStream(result.getContent)
      val doc = Jsoup.parse(input, null, result.getFetchedUrl)
      val article = articleParser.parse(doc)
      return Some(article)
    }
    None
  }
}
