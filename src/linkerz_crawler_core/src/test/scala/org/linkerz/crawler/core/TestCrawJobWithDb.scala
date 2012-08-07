/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core

import job.CrawlJob
import model.WebUrl
import org.scalatest.FunSuite
import grizzled.slf4j.Logging
import org.linkerz.test.spring.SpringContext
import org.linkerz.job.queue.core.Controller

/**
 * The Class TestCrawJobWithDb.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 9:46 PM
 *
 */

class TestCrawJobWithDb extends FunSuite with Logging with SpringContext {

  test("testCrawJobWithDb") {
    val controller = context.getBean("crawlerController", classOf[Controller])
    val job = new CrawlJob(new WebUrl("http://localhost/vnexpress/vnexpress.net/"))
    controller.add(job)
    controller.stop()
  }

}
