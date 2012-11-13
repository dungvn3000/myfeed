/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

import org.junit.{Assert, Test}
import org.linkerz.crawler.core.factory.{ParserFactory, DefaultDownloadFactory}
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.job.queue.controller.BaseController
import org.linkerz.crawler.core.handler.CrawlerHandler
import parser.GenKPlugin

/**
 * The Class TestGenKPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 9/5/12 11:21 AM
 *
 */
class TestGenKPlugin {

  val downloader = new DefaultDownloadFactory().createDownloader()
  val plugin = new GenKPlugin

  @Test
  def testPlugin() {
    val crawlJob = new CrawlJob("http://genk.vn/pc-do-choi-so/macbook-air-gia-tu-679-usd-tren-apple-store-2012090401511183.chn")
    downloader.download(crawlJob)
    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    Assert.assertEquals("MacBook Air giá từ 679 USD trên Apple Store", webPage.title)
    Assert.assertEquals("http://genk2.vcmedia.vn/Hx5Pawrt3sWkCXFccccccccccccOU/Image/2012/08/apple-macbook-jpg-1346730695_480x0-8dc3f.jpg", webPage.featureImageUrl.get)
    Assert.assertEquals(true, webPage.text.isDefined)
  }

}
