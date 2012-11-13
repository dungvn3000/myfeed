package org.linkerz.crawler.bot.plugin

import org.linkerz.crawler.core.factory.DefaultDownloadFactory
import org.scalatest.FunSuite
import org.linkerz.crawler.core.parser.Parser
import org.linkerz.crawler.core.job.CrawlJob
import org.junit.Assert

/**
 * The Class PluginParserTest.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 4:49 PM
 *
 */
trait PluginParserTest[P <: Parser] extends FunSuite {

  val downloader = new DefaultDownloadFactory().createDownloader()
  private var _parser: P = _
  private var _testUrl: String = _
  private var _testTitle: String = _
  private var _imgUrl: String = _
  private var _contentText: String = _

  def withParser(parser: P) {
    _parser = parser
  }

  def withUrl(url: String) {
    _testUrl = url
  }

  def withImgUrl(imgUrl: String) {
    _imgUrl = imgUrl
  }

  def withTitle(title: String) {
    _testTitle = title
  }

  def mustContains(text: String) {
    _contentText = text
    val crawlJob = new CrawlJob(_testUrl)
    downloader.download(crawlJob)
    _parser.parse(crawlJob)

    val webPage = crawlJob.result.get

    Assert.assertEquals(_testTitle, webPage.title)
    Assert.assertEquals(_imgUrl, webPage.featureImageUrl.get)
    Assert.assertEquals(true, webPage.text.get.contains(_contentText))
  }
}
