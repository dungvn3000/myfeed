/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.parser

import org.linkerz.crawler.core.factory.DefaultDownloadFactory
import org.linkerz.crawler.core.job.CrawlJob
import org.junit.{Assert, Test}

/**
 * The Class TestVnExpressPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:59 PM
 *
 */
class TestVnExpressPlugin {

  val downloader = new DefaultDownloadFactory().createDownloader()
  val plugin = new VnExpressParser

  @Test
  def testPlugin() {
    val crawlJob = new CrawlJob("http://vnexpress.net/gl/kinh-doanh/2012/08/phong-loa-doi-no-dai-gia-dieu-hien/")
    downloader.download(crawlJob)
//    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    Assert.assertEquals("Phóng loa đòi nợ đại gia Diệu Hiền", webPage.title)
    Assert.assertEquals("http://vnexpress.net/Files/Subject/3b/bd/ab/d7/biet_thu_ba_Dieu_Hien.jpg", webPage.featureImageUrl.get)
    Assert.assertEquals(true, webPage.text.isDefined)
  }

}
