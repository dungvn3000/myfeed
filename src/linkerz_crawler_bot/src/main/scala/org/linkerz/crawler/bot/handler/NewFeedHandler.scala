/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.handler

import org.linkerz.crawler.core.handler.CrawlerHandler
import org.linkerz.crawler.core.worker.CrawlWorker
import org.linkerz.crawler.core.factory.{ParserFactory, DownloadFactory}

/**
 * The Class NewFeedHandler.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 4:59 AM
 *
 */

class NewFeedHandler extends CrawlerHandler {

  def this(numberOfWorker: Int, downloadFactory: DownloadFactory, parserFactory: ParserFactory) {
    this
    assert(numberOfWorker > 0, "Number of worker of a handler must more than one")
    for (i <- 1 to numberOfWorker) {
      val worker = new CrawlWorker(i, downloadFactory.createDownloader(), parserFactory.createParser())
      workers += worker
    }
  }

}
