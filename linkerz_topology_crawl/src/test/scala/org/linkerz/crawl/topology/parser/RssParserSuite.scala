package org.linkerz.crawl.topology.parser

import org.scalatest.FunSuite
import org.linkerz.crawl.topology.factory.{DownloaderFactory, ParserFactory}
import org.linkerz.crawl.topology.model.WebUrl

/**
 * The Class RssParserSuite.
 *
 * @author Nguyen Duc Dung
 * @since 2/10/13 12:41 PM
 *
 */
class RssParserSuite extends FunSuite {

  test("test rss parser") {
    val downloader = DownloaderFactory.createDefaultDownloader()
    val parser = ParserFactory.createRssParser()

    val url = new WebUrl("http://vnexpress.net/rss/gl/trang-chu.rss")

    val response = downloader.download(url)
    val entries = parser.parse(response)

    assert(!entries.isEmpty)
  }

}
