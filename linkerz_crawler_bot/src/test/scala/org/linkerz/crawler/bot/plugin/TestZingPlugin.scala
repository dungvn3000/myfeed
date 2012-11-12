/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.crawler.core.factory.DefaultDownloadFactory
import org.linkerz.crawler.core.job.CrawlJob
import org.junit.{Assert, Test}
import parser.ZingPlugin

/**
 * The Class TestZingPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/21/12, 3:14 AM
 *
 */
class TestZingPlugin {

  val downloader = new DefaultDownloadFactory().createDownloader()
  val plugin = new ZingPlugin

  @Test
  def testPlugin() {
    val crawlJob = new CrawlJob("http://news.zing.vn/xa-hoi/cung-dien-tram-ty-cua-trum-ma-tuy-thac-loan/a267960.html")
    downloader.download(crawlJob)
    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    println(webPage.text)

    Assert.assertEquals("'Cung điện' trăm tỷ của trùm ma túy thác loạn", webPage.title)
    Assert.assertEquals("Biệt thự giống cung điện với tường rào kiên cố theo kiểu \"Vạn lý trường thành\", trên là lối đi, dưới là đường hầm." +
      " Trong khuôn viên 7.000m2 có cả nhà sàn, nhà gỗ, biệt thự hiện đại, bể bơi, ao cá, hồ nước...", webPage.description.get)
    Assert.assertEquals("http://img2.news.zing.vn/2012/08/16/c1.jpg", webPage.featureImageUrl.get)
  }

}
