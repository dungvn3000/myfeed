/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.handler

import org.linkerz.job.queue.handler.AsyncHandler
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.job.CrawlJob._
import org.linkerz.crawler.core.session.CrawlSession
import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.core.worker.CrawlWorker
import org.linkerz.crawler.db.DBService
import reflect.BeanProperty
import org.linkerz.crawler.core.factory.{ParserFactory, DownloadFactory}
import java.util.regex.Pattern
import org.linkerz.crawler.core.model.WebUrl
import org.apache.commons.lang.StringUtils
import org.linkerz.core.matcher.SimpleRegexMatcher
import org.linkerz.crawler.core.fetcher.DefaultFetcher

/**
 * The Class CrawlerHandler.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:54 AM
 *
 */

class CrawlerHandler extends AsyncHandler[CrawlJob, CrawlSession] {

  private val filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
    + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$")

  @BeanProperty
  var downloadFactory: DownloadFactory = _

  @BeanProperty
  var parserFactory: ParserFactory = _

  @BeanProperty
  var dbService: DBService = _

  @BeanProperty
  var maxDepth = 0

  @BeanProperty
  var onlyCrawlInSameDomain = true

  @BeanProperty
  var urlRegex: List[String] = _

  @BeanProperty
  var excludeUrl: List[String] = _

  private var session: CrawlSession = _

  def sessionClass = classOf[CrawlSession]

  def accept(job: Job) = job.isInstanceOf[CrawlJob]

  protected def createWorker(numberOfWorker: Int) {
    assert(numberOfWorker > 0, "Number of worker of a handler must more than one")
    assert(downloadFactory != null)
    assert(parserFactory != null)
    for (i <- 1 to numberOfWorker) {
      val worker = new CrawlWorker(i, new DefaultFetcher(downloadFactory, parserFactory))
      workers += worker
    }
  }

  override protected def doHandle(job: CrawlJob, session: CrawlSession) {
    this.session = session
    session.crawlTime = System.currentTimeMillis
    super.doHandle(job, session)
    session.crawlTime = System.currentTimeMillis - session.crawlTime
    println(session.countUrl + " links found")
    println(session.fetchedUrls.size + " links downloaded")
    println(session.currentDepth + " level")
    println(session.crawlTime + " ms")
    println(job.error.length + " error found")
    job.error.foreach(error => {
      println("error = " + error)
    })
  }

  protected def createSubJobs(job: CrawlJob) {
    val jobResult = job.result
    if (!jobResult.isEmpty && !jobResult.get.isError) {
      val webPage = jobResult.get
      val webUrls = webPage.webUrls
      session.countUrl += webUrls.size
      session.fetchedUrls += job.webUrl

      //Set the parent for the website.
      if (!job.parent.isEmpty) {
        val parentWebPage = job.parent.get.result.get
        webPage.parent = parentWebPage
      }

      if (dbService != null) {
        //Store the website into the database
        dbService.save(webPage)
      }

      if (job.depth > session.currentDepth) {
        session.currentDepth = job.depth
      }

      if (session.currentDepth < maxDepth) {
        webUrls.foreach(webUrl => {
          if (shouldCrawl(webUrl)) {
            subJobQueue += new CrawlJob(webUrl, job)
          }
        })
      }
    } else if (!jobResult.isEmpty && jobResult.get.isRedirect) {
      val movedUrl = jobResult.get.webUrl.movedToUrl
      if (StringUtils.isNotBlank(movedUrl)) {
        val newWebUrl = new WebUrl(movedUrl)
        if (shouldCrawl(newWebUrl)) {
          subJobQueue += new CrawlJob(newWebUrl, job)
        }
      }
    }
  }

  protected def shouldCrawl(webUrl: WebUrl): Boolean = {

    if (filters.matcher(webUrl.url).matches()) return false

    //Only crawl the url is match with url regex
    if (urlRegex != null) urlRegex.foreach(regex => {
      if (!SimpleRegexMatcher.matcher(webUrl.url, regex)) {
        return false
      }
    })

    //Not crawl the exclude url
    if (excludeUrl != null) excludeUrl.foreach(regex => {
      if (SimpleRegexMatcher.matcher(webUrl.url, regex)) {
        return false
      }
    })

    //Only crawl in same domain.
    if (!onlyCrawlInSameDomain
      || (onlyCrawlInSameDomain && webUrl.domainName == session.domainName)) {
      //Make sure we not fetch a link we did already.
      if (session.fetchedUrls.findEntry(webUrl).isEmpty) {
        //And make sure the url is not in the queue
        val result = subJobQueue.realQueue.find(job => job.webUrl == webUrl)
        if (result.isEmpty) {
          return true
        }
      }
    }
    false
  }

  override protected def readJobConfig(job: CrawlJob) {
    super.readJobConfig(job)
    if (!jobConfig.isEmpty) {
      if (!jobConfig.get(MAX_DEPTH).isEmpty) {
        maxDepth = jobConfig.get(MAX_DEPTH).get.asInstanceOf[Int]
      }
      if (!jobConfig.get(ONLY_CRAWL_IN_SAME_DOMAIN).isEmpty) {
        onlyCrawlInSameDomain = jobConfig.get(ONLY_CRAWL_IN_SAME_DOMAIN).get.asInstanceOf[Boolean]
      }
      if (!jobConfig.get(URL_REGEX).isEmpty) {
        urlRegex = jobConfig.get(URL_REGEX).get.asInstanceOf[List[String]]
      }
      if (!jobConfig.get(EXCLUDE_URL).isEmpty) {
        excludeUrl = jobConfig.get(EXCLUDE_URL).get.asInstanceOf[List[String]]
      }
    }
  }

  override def onFailed(source: Any, ex: Exception) {
    super.onFailed(source, ex)
    //Logging error
    session.job.error(ex.getMessage)
  }
}
