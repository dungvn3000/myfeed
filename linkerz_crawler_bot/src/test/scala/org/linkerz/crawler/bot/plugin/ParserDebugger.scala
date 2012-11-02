/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.crawler.core.job.CrawlJob
import grizzled.slf4j.Logging

/**
 * The Class ParserDebugger.
 *
 * @author Nguyen Duc Dung
 * @since 9/5/12 12:35 PM
 *
 */
trait ParserDebugger extends ParserPlugin {

  override def onFinished(crawlJob: CrawlJob) {
    val webPage = crawlJob.result.get
    info("Title " + webPage.title)
    info("Description " + webPage.description)
    info("Image " + webPage.featureImageUrl)
  }

}
