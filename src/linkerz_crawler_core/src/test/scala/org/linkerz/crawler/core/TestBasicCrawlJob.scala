/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core

import controller.CrawlerController
import handler.CrawlerHandler
import job.CrawlJob
import org.scalatest.FunSuite
import grizzled.slf4j.Logging
import worker.CrawlWorker

/**
 * The Class TestBasicCrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 2:47 AM
 *
 */

class TestBasicCrawlJob extends FunSuite with Logging {

  test("testBasicJob") {
    val controller = new CrawlerController
    val worker = new CrawlWorker
    val handler = new CrawlerHandler
    handler.workers += worker
    controller.handlers += handler

    controller.start()
    val job = new CrawlJob("http://vnexpress.net/")
    controller.add(job)

    Thread.sleep(5000)

    controller.stop()
  }

}
