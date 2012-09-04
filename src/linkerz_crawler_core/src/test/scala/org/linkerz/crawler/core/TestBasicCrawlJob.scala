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
import org.junit.{Assert, Test}
import org.linkerz.job.queue.core.JobStatus
import com.rabbitmq.client.ConnectionFactory

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
    val controller = new CrawlerController
    val connectionFactory = new ConnectionFactory()
    connectionFactory.setHost("127.0.0.1")
    controller.connectionFactory = connectionFactory
    val handler = new CrawlerHandler
    handler.downloadFactory = new DefaultDownloadFactory
    handler.parserFactory = new DefaultParserFactory
    controller.handlers = List(handler)
    controller.start()

    val job = new CrawlJob("http://vnexpress.net")
    job.numberOfWorker = 10
    job.maxSubJob = 100

    controller ! job
    controller.stop()

    Assert.assertEquals(JobStatus.DONE, job.status)
  }

}
