/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.crawler.core.factory.{ParserFactory, DefaultDownloadFactory}
import parser.JavaDZoneParser
import org.junit.Test
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.job.queue.controller.BaseController
import org.linkerz.crawler.core.handler.CrawlerHandler

/**
 * The Class TestJavaDzonePlugin.
 *
 * @author Nguyen Duc Dung
 * @since 10/2/12 9:18 PM
 *
 */
class TestJavaDzonePlugin {

  val downloader = new DefaultDownloadFactory().createDownloader()
  val plugin = new JavaDZoneParser

  @Test
  def testPlugin() {
    val crawlJob = new CrawlJob("http://java.dzone.com/articles/soa-service-design-cheat-sheet")
    downloader.download(crawlJob)
    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    println("webPage = " + webPage.title)
    println("webPage = " + webPage.text.get)
    println("webPage = " + webPage.featureImageUrl)
  }

}
