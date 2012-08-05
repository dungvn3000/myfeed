/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.handler

import org.linkerz.job.queue.handler.AsyncHandler
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.session.CrawlSession
import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.core.worker.CrawlWorker
import org.linkerz.crawler.core.model.{WebPage, WebUrl}
import collection.mutable
import collection.mutable.ListBuffer
import org.linkerz.crawler.db.DBService
import reflect.BeanProperty

/**
 * The Class CrawlerHandler.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:54 AM
 *
 */

class CrawlerHandler extends AsyncHandler[CrawlJob, CrawlSession] {

  private var countUrl = 0
  private var maxDepth = 0

  /**
   * Store fetched urls list
   */
  private var fetchedUrls = mutable.HashSet.empty[WebUrl]

  /**
   * Store fetched web page list
   */
  private var fetchedWeb = new ListBuffer[WebPage]

  @BeanProperty
  var dbService: DBService = _

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
    println(fetchedUrls.size + " links downloaded")
    println(maxDepth + " level")

    if (dbService != null) {
      //Store fetched website the the database.
      dbService.save(fetchedWeb.toList)
    }
  }

  protected def createSubJobs(job: CrawlJob) {
    if (!job.result.isEmpty) {
      countUrl += job.result.get.parserResult.webUrls.size
      fetchedUrls += job.webUrl
      fetchedWeb += job.result.get.parserResult.webPage

      val depth = job.depth + 1
      if (depth > maxDepth) maxDepth = depth
      job.result.get.parserResult.webUrls.foreach(webUrl => {
        //Make sure we not fectch a link we did already.
        if (fetchedUrls.findEntry(webUrl).isEmpty) {
          subJobQueue += new CrawlJob(webUrl, job)
        }
      })
    }
  }
}
