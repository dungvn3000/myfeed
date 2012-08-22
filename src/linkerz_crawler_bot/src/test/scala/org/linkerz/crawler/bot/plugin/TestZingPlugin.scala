/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext
import org.linkerz.crawler.core.factory.DefaultDownloadFactory
import org.linkerz.crawler.core.job.CrawlJob
import zing.ZingPlugin
import org.linkerz.test.categories.ManualTest
import org.junit.experimental.categories.Category

/**
 * The Class TestZingPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/21/12, 3:14 AM
 *
 */
@Category(Array(classOf[ManualTest]))
class TestZingPlugin extends FunSuite with SpringContext {

  val downloader = new DefaultDownloadFactory().createDownloader()
  val plugin = new ZingPlugin

  test("testPlugin") {
    val crawlJob = new CrawlJob("http://news.zing.vn/xa-hoi/cung-dien-tram-ty-cua-trum-ma-tuy-thac-loan/a267960.html")
    downloader.download(crawlJob)
    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    println("Title = " + webPage.title)
    println("Desciption = " + webPage.description)
    println("Image = " + webPage.featureImageUrl)
  }

}
