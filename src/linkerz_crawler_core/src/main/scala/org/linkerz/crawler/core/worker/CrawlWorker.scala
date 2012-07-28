/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.worker

import org.linkerz.job.queue.core.Worker
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.session.CrawlSession

/**
 * The Class CrawlWorker.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 2:40 AM
 *
 */

class CrawlWorker extends Worker[CrawlJob, CrawlSession] {

  def analyze(job: CrawlJob, session: CrawlSession) = {
    println(job.url)
    null
  }
}
