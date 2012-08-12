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
import org.linkerz.crawler.core.downloader.{DefaultDownload, Downloader}
import org.linkerz.crawler.core.parser.{DefaultParser, Parser}

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
  private var maxDepth = 0

  private var _downloadClass: Class[_ <: Downloader] = classOf[DefaultDownload]
  private var _parserClass: Class[_ <: Parser] = classOf[DefaultParser]

  /**
   * Store fetched urls list
   */
  private var fetchedUrls = mutable.HashSet.empty[WebUrl]

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
      val worker = new CrawlWorker(i, downloader, parser)
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
    println(maxDepth + " level")
    println(crawlTime + " ms")
  }

  protected def createSubJobs(job: CrawlJob) {
    if (!job.result.isEmpty) {
      countUrl += job.result.get.parserResult.webPage.webUrls.size
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

      val depth = job.depth + 1
      if (depth > maxDepth) maxDepth = depth
      job.result.get.parserResult.webPage.webUrls.foreach(webUrl => {
        //Make sure we not fectch a link we did already.
        if (fetchedUrls.findEntry(webUrl).isEmpty) {
          subJobQueue += new CrawlJob(webUrl, job)
        }
      })
    }
  }

  def downloader = {
    _downloadClass.newInstance()
  }

  def parser = {
    _parserClass.newInstance()
  }

  def downloadClass = _downloadClass

  def downloadClass_=(downloadClass: Class[_ <: Downloader]) {
    _downloadClass = downloadClass
  }

  def parserClass = _parserClass

  def parserClass_=(parserClass: Class[_ <: Parser]) {
    _parserClass = parserClass
  }
}
