package org.linkerz.crawl.topology.parser

import org.scalatest.FunSuite
import org.linkerz.crawl.topology.factory.{ParserFactory, DownloaderFactory}
import org.linkerz.crawl.topology.model.WebUrl

/**
 * The Class WebPageParserSuite.
 *
 * @author Nguyen Duc Dung
 * @since 2/10/13 12:48 PM
 *
 */
class WebPageParserSuite extends FunSuite {

  test("test webpage parser") {
    val url = WebUrl("http://vnexpress.net/gl/the-gioi/cuoc-song-do-day/2013/02/chau-a-se-lot-xac-trong-nam-ran-1/")
    val downloader = DownloaderFactory.createWebPageDownloader()
    val parser = ParserFactory.createWebPageParser()

    val webPage = downloader.download(url).getOrElse(throw new Exception("Can't download the url"))
    parser.parse(webPage)

    assert(webPage.isArticle)
    assert(!webPage.potentialImages.isEmpty)
    assert(!webPage.title.isEmpty)
    assert(!webPage.text.isEmpty)
    assert(!webPage.description.isEmpty)
  }

}
