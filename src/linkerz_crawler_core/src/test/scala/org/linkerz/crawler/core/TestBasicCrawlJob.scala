/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core

import controller.CrawlerController
import factory.{DefaultParserFactory, DefaultDownloadFactory}
import handler.CrawlerHandler
import job.CrawlJob
import org.scalatest.FunSuite
import grizzled.slf4j.Logging
import org.linkerz.test.categories.ManualTest
import org.junit.experimental.categories.Category

/**
 * The Class TestBasicCrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 2:47 AM
 *
 */
@Category(Array(classOf[ManualTest]))
class TestBasicCrawlJob extends FunSuite with Logging {

  test("testBasicJob") {
    val controller = new CrawlerController
    val handler = new CrawlerHandler()
    handler.downloadFactory = new DefaultDownloadFactory
    handler.parserFactory = new DefaultParserFactory

    controller.handlers = List(handler)

    controller.start()
    val job = new CrawlJob("http://vnexpress.net/gl/24h-qua/")
    controller ! job
    controller.stop()
  }

}
