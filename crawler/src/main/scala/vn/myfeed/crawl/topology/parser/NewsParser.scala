package vn.myfeed.crawl.topology.parser

import com.github.sonic.parser.{HtmlExtractor, ArticleParser}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import vn.myfeed.crawl.topology.downloader.DownloadResult
import org.horrabin.horrorss.RssItemBean

/**
 * The this class using two parser LinksParse and ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:15 PM
 *
 */
class NewsParser {

  val articleParser = new ArticleParser

  val htmlExtractor = new HtmlExtractor

  def parse(result: DownloadResult) = {
    val input = new ByteArrayInputStream(result.content)
    val doc = Jsoup.parse(input, null, result.url)
    articleParser.parse(doc)
  }

  def extract(item: RssItemBean) = {
    val doc = Jsoup.parse(item.getDescription, item.getLink)
    htmlExtractor.extract(doc)
    htmlExtractor
  }
}
