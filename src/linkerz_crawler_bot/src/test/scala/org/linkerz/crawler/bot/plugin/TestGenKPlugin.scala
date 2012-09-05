/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import genk.GenKPlugin
import org.junit.{Assert, Test}
import org.linkerz.crawler.core.factory.{ParserFactory, DefaultParserFactory, DefaultDownloadFactory}
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.job.queue.controller.BaseController
import org.linkerz.crawler.core.handler.CrawlerHandler
import org.linkerz.job.queue.core.JobStatus

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
    val crawlJob = new CrawlJob("http://genk.vn/c188n2012090401511183/macbook-air-gia-tu-679-usd-tren-apple-store.chn")
    downloader.download(crawlJob)
    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    Assert.assertEquals("MacBook Air giá từ 679 USD trên Apple Store", webPage.title)
    Assert.assertEquals("Các mẫu laptop siêu mỏng của Apple thế hệ ra mắt năm 2010 được bán dưới dạng hàng tân trang lại " +
      "(refurbished) có cấu hình khá với chip Core 2 Duo, RAM...", webPage.description)
    Assert.assertEquals("http://genk2.vcmedia.vn/Hx5Pawrt3sWkCXFccccccccccccOU/Image/2012/08/apple-macbook-jpg-1346730695_480x0-8dc3f.jpg", webPage.featureImageUrl)
  }

  @Test
  def testWith100Links() {
    val controller = new BaseController
    val handler = new CrawlerHandler
    handler.downloadFactory = new DefaultDownloadFactory
    handler.parserFactory = new DefaultParserFactory
    controller.handlers = List(handler)
    controller.start()

    val job = new CrawlJob(plugin.defaultData.urlTest)
    job.numberOfWorker = 10
    job.maxSubJob = 100
    job.maxDepth = 1

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)
  }

}
