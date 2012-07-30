/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.handler

import org.linkerz.job.queue.handler.AsyncHandler
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.session.CrawlSession
import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.core.worker.CrawlWorker

/**
 * The Class CrawlerHandler.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:54 AM
 *
 */

class CrawlerHandler extends AsyncHandler[CrawlJob, CrawlSession] {

  private var countUrl = 0
  private var countWeb = 0

  /**
   * Construct a handler with number of worker.
   * @param numberOfWorker must greater than one
   */
  def this(numberOfWorker: Int) {
    this
    assert(numberOfWorker > 0, "Number of worker of a handler must more than one")
    for (i <- 1 to numberOfWorker) {
      val worker = new CrawlWorker(i)
      workers += worker
    }
  }

  def sessionClass = classOf[CrawlSession]

  def accept(job: Job) = job.isInstanceOf[CrawlJob]

  override protected def doHandle(job: CrawlJob, session: CrawlSession) {
    super.doHandle(job, session)
    println(countUrl + " links found")
    println(countWeb + " web pages downloaded")
  }

  protected def createSubJobs(job: CrawlJob) {
    if (!job.result.isEmpty) {
      countUrl += job.result.get.parserResult.webUrls.size
      countWeb += 1
      job.result.get.parserResult.webUrls.foreach(webUrl => {
        subJobQueue += new CrawlJob(webUrl)
      })
    }
  }
}
