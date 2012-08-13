/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.handler

import org.linkerz.job.queue.handler.AsyncHandler
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.session.CrawlSession
import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.core.worker.CrawlWorker
import org.linkerz.crawler.core.model.WebUrl
import collection.mutable
import org.linkerz.crawler.db.DBService
import reflect.BeanProperty
import org.linkerz.crawler.core.downloader.DefaultDownload
import org.linkerz.crawler.core.parser.DefaultParser

/**
 * The Class CrawlerHandler.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:54 AM
 *
 */

class CrawlerHandler extends AsyncHandler[CrawlJob, CrawlSession] {

  private var crawlTime: Long = 0
  private var countUrl = 0
  private var currentDepth = 0

  /**
   * Store fetched urls list
   */
  private var fetchedUrls = mutable.HashSet.empty[WebUrl]

  @BeanProperty
  var dbService: DBService = _

  @BeanProperty
  var maxDepth = 0

  /**
   * Construct a handler with number of worker.
   * @param numberOfWorker must greater than one
   */
  def this(numberOfWorker: Int) {
    this
    assert(numberOfWorker > 0, "Number of worker of a handler must more than one")
    for (i <- 1 to numberOfWorker) {
      val worker = new CrawlWorker(i, new DefaultDownload, new DefaultParser)
      workers += worker
    }
  }

  def sessionClass = classOf[CrawlSession]

  def accept(job: Job) = job.isInstanceOf[CrawlJob]

  override protected def doHandle(job: CrawlJob, session: CrawlSession) {
    crawlTime = System.currentTimeMillis
    super.doHandle(job, session)
    crawlTime = System.currentTimeMillis - crawlTime
    println(countUrl + " links found")
    println(fetchedUrls.size + " links downloaded")
    println(currentDepth + " level")
    println(crawlTime + " ms")
  }

  protected def createSubJobs(job: CrawlJob) {
    if (!job.result.isEmpty) {
      val webUrls = job.result.get.parserResult.webPage.webUrls
      countUrl += webUrls.size
      fetchedUrls += job.webUrl

      //Set the parent for the website.
      if (!job.parent.isEmpty) {
        val parent = job.parent.get.result.get
        val parentWebPage = parent.parserResult.webPage
        job.result.get.parserResult.webPage.parent = parentWebPage
      }

      if (dbService != null) {
        //Store the website into the database
        dbService.save(job.result.get.parserResult.webPage)
      }

      if (job.depth > currentDepth) {
          currentDepth = job.depth
      }

      if (currentDepth < maxDepth) {
        webUrls.foreach(webUrl => {
          //Make sure we not fetch a link we did already.
          if (fetchedUrls.findEntry(webUrl).isEmpty) {
            //And make sure the url is not in the queue
            val result = subJobQueue.realQueue.find(job => job.webUrl == webUrl)
            if (result.isEmpty) {
              subJobQueue += new CrawlJob(webUrl, job)
            }
          }
        })
      }
    }
  }
}
