/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.crawler.core.factory.{ParserFactory, DefaultDownloadFactory}
import parser.TwentyFourHourPlugin
import org.junit.{Assert, Test}
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.job.queue.controller.BaseController
import org.linkerz.crawler.core.handler.CrawlerHandler

/**
 * The Class TestTwentyFourHourPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 10/4/12 10:35 PM
 *
 */
class TestTwentyFourHourPlugin {

  val downloader = new DefaultDownloadFactory().createDownloader()
  val plugin = new TwentyFourHourPlugin

  @Test
  def testPlugin() {
    val crawlJob = new CrawlJob("http://hcm.24h.com.vn/tin-tuc-trong-ngay/lang-ung-thu-o-ha-noi-c46a488328.html")
    downloader.download(crawlJob)
    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    Assert.assertEquals("\"Thôn ung thư\" ngắc ngoải giữa lòng HN", webPage.title)
    Assert.assertEquals("(Tin tuc) - Người ở đây gọi nơi họ đang sống là thôn ung thư. " +
      "Trong thôn ung thư lại có ngõ ung thư. Chỉ một con ngõ chừng hơn 10 gia đình sinh sống mà có " +
      "đến 5-6 nhà có người chết vì bệnh ung thư.", webPage.description.get)
    Assert.assertEquals("http://img-hcm.24hstatic.com/upload/4-2012/images/2012-10-04/1349338673_lang-ung-thu-200.jpg", webPage.featureImageUrl.get)
  }

  @Test
  def testWith100Links() {
    val controller = new BaseController
    val handler = new CrawlerHandler(
      parserFactory = new ParserFactory {
        def createParser() = new TwentyFourHourPlugin with ParserDebugger
      }
    )
    controller.handlers = List(handler)
    controller.start()

    val job = new CrawlJob(plugin.pluginData.urlTest)
    job.maxSubJob = 100

    controller ! job
    controller.stop()
  }


}
