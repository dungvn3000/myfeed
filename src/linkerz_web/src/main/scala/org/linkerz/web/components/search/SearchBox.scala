/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.components.search

import org.apache.tapestry5.annotations.{Component, Property}
import org.apache.tapestry5.corelib.components.{Form, TextField}
import org.linkerz.crawler.core.controller.CrawlerController
import org.linkerz.crawler.core.handler.CrawlerHandler
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class SearchBox.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 2:00 AM
 *
 */

class SearchBox {

  @Property
  private var keyWord: String = _

  @Component
  private var txtSearch: TextField = _

  @Component
  private var search: Form = _

  def onSubmit() {
    println(keyWord)
    val controller = new CrawlerController
    val handler = new CrawlerHandler(5)
    //Limit retry
    handler.maxRetry = 10
    //Ide time
    handler.ideTime = 1000
    controller.handlers = List(handler)

    controller.start()
    val job = new CrawlJob(new WebUrl(keyWord))
    controller.add(job)
    controller.stop()
  }
}
