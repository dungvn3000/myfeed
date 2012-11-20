/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core

import factory.{DefaultParserFactory, DefaultDownloadFactory}
import handler.CrawlerHandler
import job.CrawlJob
import org.junit.Assert
import org.linkerz.job.queue.core.JobStatus
import org.linkerz.job.queue.controller.BaseController
import org.scalatest.FunSuite
import org.linkerz.model.LinkDao
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class BasicCrawlJobSuite.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 2:47 AM
 *
 */
class BasicCrawlJobSuite extends FunSuite {

  test("test with 100 vnexpress links") {
    val controller = new BaseController
    val handler = new CrawlerHandler
    controller.handlers = List(handler)
    controller.start()

    val job = new CrawlJob("http://vnexpress.net")
    job.maxSubJob = 100

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)
  }

  test("test time out") {
    val controller = new BaseController
    val handler = new CrawlerHandler
    controller.handlers = List(handler)
    controller.start()

    val job = new CrawlJob("http://vnexpress.net")
    job.timeOut = 5000

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.ERROR, job.status)
  }

  test("test max depth on 24h.com.vn") {
    val controller = new BaseController
    val handler = new CrawlerHandler
    controller.handlers = List(handler)
    controller.start()

    val job = new CrawlJob("http://hcm.24h.com.vn/")
    job.maxDepth = 1

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)
  }

  ignore("test store crawling result into database") {
    val controller = new BaseController
    val handler = new CrawlerHandler(usingDB = true)
    controller.handlers = List(handler)
    controller.start()

    val job = new CrawlJob("http://genk.vn/")
    job.maxSubJob = 100

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)

    val count = LinkDao.count()
    assert(count == 100)

    LinkDao.remove(MongoDBObject.empty)
  }

}
