/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.handler

import org.linkerz.crawler.core.handler.CrawlerHandler
import org.linkerz.crawler.core.worker.CrawlWorker
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.bot.job.NewFeedJob
import akka.actor.{Props, ActorContext}
import akka.routing.RoundRobinRouter
import org.linkerz.crawler.core.factory.{ParserFactory, DownloadFactory}
import org.linkerz.crawler.bot.fetcher.NewFetcher
import org.linkerz.model.{LoggingDao, LinkDao}
import org.linkerz.logger.DBLogger

/**
 * The Class NewFeedHandler.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 4:59 AM
 *
 */

class NewFeedHandler(downloadFactory: DownloadFactory, parserFactory: ParserFactory)
  extends CrawlerHandler(downloadFactory, parserFactory, true) {

  override protected def createWorker(context: ActorContext) = {
    context.actorOf(Props(new CrawlWorker(new NewFetcher(downloadFactory, parserFactory))).
      withRouter(RoundRobinRouter(5)))
  }

  override protected def shouldCrawl(webUrl: WebUrl) = LinkDao.findByUrl(webUrl.url).isEmpty && super.shouldCrawl(webUrl)


  override def accept(job: Job) = job.isInstanceOf[NewFeedJob]

  override protected def onFinish() {
    super.onFinish()
    //Store error into the database
    LoggingDao.insert(currentSession.job.errors)
  }
}
