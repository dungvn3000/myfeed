/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core

import controller.CrawlerController
import handler.CrawlerHandler
import job.CrawlJob
import model.WebUrl
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
    val handler = new CrawlerHandler

    //Setup five worker.
    for(i <- 1 to 5) {
      val worker = new CrawlWorker
      handler.workers += worker
    }

    controller.handlers += handler

    controller.start()
    val job = new CrawlJob(new WebUrl("http://vnexpress.net/"))
    controller.add(job)
    controller.stop()
  }

}
