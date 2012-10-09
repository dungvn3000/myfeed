/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core

import factory.{DefaultParserFactory, DefaultDownloadFactory}
import handler.CrawlerHandler
import job.CrawlJob
import grizzled.slf4j.Logging
import org.junit.{Assert, Test}
import org.linkerz.job.queue.core.JobStatus
import org.linkerz.job.queue.controller.BaseController

/**
 * The Class TestBasicCrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 2:47 AM
 *
 */
class TestBasicCrawlJob extends Logging {

  @Test
  def testBasicJob {
    val controller = new BaseController
    val handler = new CrawlerHandler
    handler.downloadFactory = new DefaultDownloadFactory
    handler.parserFactory = new DefaultParserFactory
    controller.handlers = List(handler)
    controller.start()

    val job = new CrawlJob("http://vnexpress.net")
    job.maxSubJob = 100

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)
  }

}
