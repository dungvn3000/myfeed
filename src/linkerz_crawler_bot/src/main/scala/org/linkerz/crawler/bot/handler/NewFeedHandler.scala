/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.handler

import org.linkerz.crawler.core.handler.CrawlerHandler
import org.linkerz.crawler.core.worker.CrawlWorker
import org.linkerz.crawler.core.factory.{ParserFactory, DownloadFactory}
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.crawler.bot.fetcher.NewFetcher
import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.bot.job.NewFeedJob

/**
 * The Class NewFeedHandler.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 4:59 AM
 *
 */

class NewFeedHandler extends CrawlerHandler {

 // override protected def createWorker() {
//    assert(numberOfWorker > 0, "Number of actor of a handler must more than one")
//    for (i <- 1 to numberOfWorker) {
//      val actor = new CrawlWorker(i, new NewFetcher(downloadFactory, parserFactory))
//      workers += actor
//    }
 // }

  override protected def shouldCrawl(webUrl: WebUrl) = {
    dbService.find(webUrl) == null && super.shouldCrawl(webUrl)
  }

  override def accept(job: Job) = job.isInstanceOf[NewFeedJob]
}
