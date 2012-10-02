/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.junit.Test
import org.linkerz.crawler.core.factory.DefaultDownloadFactory
import parser.HOnlinePlugin
import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class TestHOnlinePlugin.
 *
 * @author Nguyen Duc Dung
 * @since 9/29/12 10:15 AM
 *
 */
class TestHOnlinePlugin {

  val downloader = new DefaultDownloadFactory().createDownloader()
  val plugin = new HOnlinePlugin

  @Test
  def testPlugin() {
    val crawlJob = new CrawlJob("http://www.h-online.com/security/news/item/Cisco-fixes-alleged-DoS-holes-1720128.html")
    downloader.download(crawlJob)
    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    println("webPage = " + webPage.title)
    println("webPage = " + webPage.description)
    println("webPage = " + webPage.featureImageUrl)
  }

}
