/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.scalatest.FunSuite
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.{ParserPluginData, Link}
import org.linkerz.test.spring.SpringContext

import collection.JavaConversions._
import vnexpress.VnExpressPlugin
import org.linkerz.crawler.core.parser.ParserResult
import org.linkerz.crawler.core.downloader.DefaultDownload
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.crawler.core.factory.DefaultDownloadFactory
import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class TestVnExpressPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:59 PM
 *
 */

class TestVnExpressPlugin extends FunSuite with SpringContext {

  val downloader = new DefaultDownloadFactory().createDownloader()
  val plugin = new VnExpressPlugin

  test("testPlugin") {
    val crawlJob = new CrawlJob("http://vnexpress.net/gl/kinh-doanh/2012/08/phong-loa-doi-no-dai-gia-dieu-hien/")
    downloader.download(crawlJob)
    plugin.parse(crawlJob)
    val webPage = crawlJob.result.get

    println("Title = " + webPage.title)
    println("Desciption = " + webPage.description)
    println("Image = " + webPage.featureImageUrl)
  }

}
