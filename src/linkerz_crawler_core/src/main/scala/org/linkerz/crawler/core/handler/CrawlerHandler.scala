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

  private var _downloadFactory: DownloadFactory = _

  private var _parserFactory: ParserFactory = _

  @BeanProperty
  var dbService: DBService = _

  @BeanProperty
  var maxDepth = 0

  @BeanProperty
  var onlyCrawlInSameDomain = true

  @BeanProperty
  var urlRegex: String = _

  @BeanProperty
  var excludeUrl: String = _

  private var _session: CrawlSession = _

  def sessionClass = classOf[CrawlSession]

  def accept(job: Job) = job.isInstanceOf[CrawlJob]

  /**
   * Construct a handler with number of worker.
   * @param numberOfWorker must greater than one
   * @param downloadFactory
   * @param parserFactory
   */
  def this(numberOfWorker: Int, downloadFactory: DownloadFactory, parserFactory: ParserFactory) {
    this
    assert(numberOfWorker > 0, "Number of worker of a handler must more than one")
    assert(downloadFactory != null)
    assert(parserFactory != null)
    _downloadFactory = downloadFactory
    _parserFactory = parserFactory
    for (i <- 1 to numberOfWorker) {
      val worker = new CrawlWorker(i, downloadFactory.createDownloader(), parserFactory.createParser())
      workers += worker
    }
  }

  override protected def doHandle(job: CrawlJob, session: CrawlSession) {
    _session = session
    _session.crawlTime = System.currentTimeMillis
    super.doHandle(job, session)
    _session.crawlTime = System.currentTimeMillis - _session.crawlTime
    println(_session.countUrl + " links found")
    println(_session.fetchedUrls.size + " links downloaded")
    println(_session.currentDepth + " level")
    println(_session.crawlTime + " ms")
  }

  protected def createSubJobs(job: CrawlJob) {
    val jobResult = job.result
    if (!jobResult.isEmpty && !jobResult.get.isError) {
      val webPage = jobResult.get
      val webUrls = webPage.webUrls
      _session.countUrl += webUrls.size
      _session.fetchedUrls += job.webUrl

      //Set the parent for the website.
      if (!job.parent.isEmpty) {
        val parentWebPage = job.parent.get.result.get
        webPage.parent = parentWebPage
      }

      if (dbService != null) {
        //Store the website into the database
        dbService.save(webPage)
      }

      if (job.depth > _session.currentDepth) {
        _session.currentDepth = job.depth
      }

      if (_session.currentDepth < maxDepth) {
        webUrls.foreach(webUrl => {
          if (shouldCrawl(webUrl)) {
            subJobQueue += new CrawlJob(webUrl, job)
          }
        })
      }
    }
  }

  protected def shouldCrawl(webUrl: WebUrl): Boolean = {

    if (filters.matcher(webUrl.url).matches()) return false

    //Only crawl the url is match with url regex
    if (StringUtils.isNotBlank(urlRegex)
      && !SimpleRegexMatcher.matcher(webUrl.url, urlRegex)) {
      return false
    }

    //Not crawl the exclude url
    if (StringUtils.isNotBlank(excludeUrl)
      && SimpleRegexMatcher.matcher(webUrl.url, excludeUrl)) {
      return false
    }


    //Only crawl in same domain.
    if (!onlyCrawlInSameDomain
      || (onlyCrawlInSameDomain && webUrl.domainName == _session.domainName)) {
      //Make sure we not fetch a link we did already.
      if (_session.fetchedUrls.findEntry(webUrl).isEmpty) {
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
        urlRegex = jobConfig.get(URL_REGEX).get.asInstanceOf[String]
      }
      if (!jobConfig.get(EXCLUDE_URL).isEmpty) {
        excludeUrl = jobConfig.get(EXCLUDE_URL).get.asInstanceOf[String]
      }
    }
  }
}
