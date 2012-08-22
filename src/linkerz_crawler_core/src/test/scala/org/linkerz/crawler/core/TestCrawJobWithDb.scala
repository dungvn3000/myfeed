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
import org.linkerz.test.categories.ManualTest
import org.junit.experimental.categories.Category

/**
 * The Class TestCrawJobWithDb.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 9:46 PM
 *
 */
@Category(Array(classOf[ManualTest]))
class TestCrawJobWithDb extends FunSuite with Logging with SpringContext {

  test("testCrawJobWithDb") {
    val controller = context.getBean("crawlerController", classOf[Controller])
    val job = new CrawlJob("http://vnexpress.net/")
    controller ! job
    controller.stop()
  }

}
