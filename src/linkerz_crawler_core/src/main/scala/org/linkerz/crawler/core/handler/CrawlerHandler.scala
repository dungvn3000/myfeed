/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core.handler

import org.linkerz.job.queue.handler.AsyncHandler
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.session.CrawlSession
import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.core.worker.CrawlWorker
import org.linkerz.crawler.db.DBService
import reflect.BeanProperty
import org.linkerz.crawler.core.factory.{ParserFactory, DownloadFactory}
import org.linkerz.crawler.core.model.WebUrl
import org.apache.commons.lang.StringUtils
import org.linkerz.core.matcher.SimpleRegexMatcher
import org.linkerz.crawler.core.fetcher.DefaultFetcher
import akka.actor.{Props, ActorContext}
import akka.routing.RoundRobinRouter
import collection.JavaConversions._

/**
 * The Class CrawlerHandler.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:54 AM
 *
 */
class CrawlerHandler extends AsyncHandler[CrawlJob, CrawlSession] {

  @BeanProperty
  var downloadFactory: DownloadFactory = _

  @BeanProperty
  var parserFactory: ParserFactory = _

  @BeanProperty
  var dbService: DBService = _

  def sessionClass = classOf[CrawlSession]

  def accept(job: Job) = job.isInstanceOf[CrawlJob]

  override protected def createWorker(context: ActorContext) = {
    assert(downloadFactory != null)
    assert(parserFactory != null)
    context.actorOf(Props(new CrawlWorker(new DefaultFetcher(downloadFactory, parserFactory))).
      withRouter(RoundRobinRouter(5)))
  }

  override protected def onFinish() {
    info(currentSession.countUrl + " links found")
    info(currentSession.fetchedUrls.size + " links downloaded")
    info(currentSession.currentDepth + " level")
    info(currentSession.jobTime + " ms")
    info(currentSession.job.error.length + " error found")
    currentSession.job.error.foreach(error => info("error = " + error))
  }

  override protected def onSuccess(job: CrawlJob) {
    val jobResult = job.result
    currentSession.fetchedUrls.add(job.webUrl)

    if (!jobResult.isEmpty && !jobResult.get.isError) {
      val webPage = jobResult.get
      val webUrls = webPage.webUrls
      currentSession.countUrl += webUrls.size

      //Set the parent for the website.
      if (!job.parent.isEmpty) {
        val parentWebPage = job.parent.get.result.get
        webPage.parent = parentWebPage
      }

      if (dbService != null) {
        //Store the website into the database
        dbService.save(webPage)
      }

      //If the manager is going to stop, we will not add any job to the queue.
      if(!isStop) {
        webUrls.foreach(webUrl => {
          if (shouldCrawl(webUrl)) {
            this ! new CrawlJob(webUrl, job)
            currentSession.queueUrls.add(webUrl)
          }
        })
      }

    } else if (!jobResult.isEmpty && jobResult.get.isRedirect && !isStop) {
      val movedUrl = jobResult.get.webUrl.movedToUrl
      if (StringUtils.isNotBlank(movedUrl)) {
        val newWebUrl = new WebUrl(movedUrl)
        if (shouldCrawl(newWebUrl)) {
          this ! new CrawlJob(newWebUrl, job)
          currentSession.queueUrls.add(newWebUrl)
        }
      }
    }
  }

  /**
   * Check the url should crawl or not.
   * @param webUrl
   * @return
   */
  protected def shouldCrawl(webUrl: WebUrl): Boolean = {

    if (currentJob.filterPattern.matcher(webUrl.url).matches()) return false

    //Only crawl the url is match with url regex
    if (currentJob.urlRegex != null) currentJob.urlRegex.foreach(regex => {
      if (!SimpleRegexMatcher.matcher(webUrl.url, regex)) {
        return false
      }
    })

    //Not crawl the exclude url
    if (currentJob.excludeUrl != null) currentJob.excludeUrl.foreach(regex => {
      if (SimpleRegexMatcher.matcher(webUrl.url, regex)) {
        return false
      }
    })

    //Only crawl in same domain.
    if (!currentJob.onlyCrawlInSameDomain
      || (currentJob.onlyCrawlInSameDomain && webUrl.domainName == currentSession.domainName)) {
      //Make sure we not fetch a link what we did already.
      if (!currentSession.fetchedUrls.contains(webUrl)) {
        //And make sure the url is not in the queue
        if (!currentSession.queueUrls.contains(webUrl)) {
          return true
        }
      }
    }
    false
  }
}
