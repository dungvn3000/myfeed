/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core.worker

import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.session.CrawlSession
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.job.queue.worker.Worker

/**
 * The Class CrawlWorker.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 2:40 AM
 *
 */

class CrawlWorker(fetcher: Fetcher) extends Worker[CrawlJob, CrawlSession] {

  def work(job: CrawlJob, session: CrawlSession) {
    fetcher.fetch(job)
  }

}
