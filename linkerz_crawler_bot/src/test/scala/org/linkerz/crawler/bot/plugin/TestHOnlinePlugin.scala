/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

import org.junit.Test
import org.linkerz.crawler.core.factory.{ParserFactory, DefaultDownloadFactory}
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.job.queue.controller.BaseController
import org.linkerz.crawler.core.handler.CrawlerHandler
import org.linkerz.crawler.bot.parser.HOnlineParser

/**
 * The Class TestHOnlinePlugin.
 *
 * @author Nguyen Duc Dung
 * @since 9/29/12 10:15 AM
 *
 */
class TestHOnlinePlugin {

  val downloader = new DefaultDownloadFactory().createDownloader()
  val plugin = new HOnlineParser

  @Test
  def testPlugin() {
    val crawlJob = new CrawlJob("http://www.h-online.com/security/news/item/Cisco-fixes-alleged-DoS-holes-1720128.html")
    downloader.download(crawlJob)
    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    println("webPage = " + webPage.title)
    println("webPage = " + webPage.text.get)
    println("webPage = " + webPage.featureImageUrl)
  }
}
