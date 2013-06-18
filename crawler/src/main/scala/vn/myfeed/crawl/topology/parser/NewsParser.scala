package vn.myfeed.crawl.topology.parser

import com.github.sonic.parser.{HtmlExtractor, ArticleParser}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import vn.myfeed.crawl.topology.downloader.DownloadResult
import org.apache.commons.lang3.StringUtils
import com.sun.syndication.feed.synd.{SyndContent, SyndEntry}
import scala.collection.JavaConversions._

/**
 * The this class using two parser LinksParse and ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:15 PM
 *
 */
class NewsParser {

  val articleParser = new ArticleParser

  def parse(result: DownloadResult, entry: SyndEntry) = {
    val input = new ByteArrayInputStream(result.content)
    val doc = Jsoup.parse(input, null, result.redirectUrl.getOrElse(result.url))
    if (StringUtils.isNotBlank(entry.getTitle)) {
      articleParser.parse(doc, entry.getTitle)
    } else {
      articleParser.parse(doc)
    }
  }

  def extract(item: SyndEntry): (Option[String], Option[String]) = {
    var html = ""
    if (item.getDescription != null
      && StringUtils.isNotBlank(item.getDescription.getValue)) {
      html = item.getDescription.getValue
    } else {
      item.getContents.foreach(content => {
        html += content.asInstanceOf[SyndContent].getValue
      })
    }
    if (StringUtils.isNotBlank(html)) {
      val htmlExtractor = new HtmlExtractor
      val doc = Jsoup.parse(html, item.getLink)
      htmlExtractor.extract(doc)
      (Some(htmlExtractor.text), Some(html))
    } else {
      (None, None)
    }
  }
}
