/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.handler

import org.linkerz.job.queue.handler.AsyncHandler
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.session.CrawlSession
import org.linkerz.job.queue.core.Job

/**
 * The Class CrawlerHandler.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:54 AM
 *
 */

class CrawlerHandler extends AsyncHandler[CrawlJob, CrawlSession] {

  private var countUrl = 0

  def sessionClass = classOf[CrawlSession]
  def accept(job: Job) = job.isInstanceOf[CrawlJob]

  override protected def doHandle(job: CrawlJob, session: CrawlSession) {
    super.doHandle(job, session)
    println(countUrl + " links found")
  }

  protected def createSubJobs(job: CrawlJob) {
    if (!job.result.isEmpty) {
      countUrl += job.result.get.size
      job.result.get.foreach(webUrl => {
        subJobQueue += new CrawlJob(webUrl)
      })
    }
  }
}
