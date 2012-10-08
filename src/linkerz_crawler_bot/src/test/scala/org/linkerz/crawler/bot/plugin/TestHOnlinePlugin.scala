/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.junit.Test
import org.linkerz.crawler.core.factory.{ParserFactory, DefaultDownloadFactory}
import parser.HOnlinePlugin
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.job.queue.controller.BaseController
import org.linkerz.crawler.core.handler.CrawlerHandler

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

  @Test
  def testWith50Links() {
    val controller = new BaseController
    val handler = new CrawlerHandler
    handler.downloadFactory = new DefaultDownloadFactory
    handler.parserFactory = new ParserFactory {
      def createParser() = new HOnlinePlugin with ParserDebugger
    }
    controller.handlers = List(handler)
    controller.start()

    val job = new CrawlJob(plugin.defaultData.urlTest)
    job.maxSubJob = 50

    controller ! job
    controller.stop()
  }

}
