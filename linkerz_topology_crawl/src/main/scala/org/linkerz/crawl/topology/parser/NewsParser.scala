package org.linkerz.crawl.topology.parser

import com.github.sonic.parser.ArticleParser
import crawlercommons.fetcher.FetchedResult
import org.apache.http.HttpStatus
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import org.linkerz.model.News
import org.bson.types.ObjectId

/**
 * The this class using two parser LinksParse and ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:15 PM
 *
 */
class NewsParser {

  val articleParser = new ArticleParser

  def parse(feedId: ObjectId, result: FetchedResult): Option[News] = {
    if (result.getStatusCode == HttpStatus.SC_OK && result.getContentLength > 0) {
      val input = new ByteArrayInputStream(result.getContent)
      val doc = Jsoup.parse(input, null, result.getFetchedUrl)
      val article = articleParser.parse(doc)

      val news = News(
        feedId = feedId,
        title = article.title,
        description = Some(article.description()),
        text = Some(article.text),
        url = result.getFetchedUrl
      )

      return Some(news)
    }
    None
  }
}
