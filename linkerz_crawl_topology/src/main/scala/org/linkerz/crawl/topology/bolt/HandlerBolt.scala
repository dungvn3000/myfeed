package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{Fetch, StartWith, Handle}
import collection.JavaConversions._
import org.linkerz.crawl.topology.model.WebUrl
import org.linkerz.crawl.topology.session.CrawlSession
import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.core.matcher.SimpleRegexMatcher
import grizzled.slf4j.Logging

/**
 * The mission of this bolt will receive job from the feed spot and emit it to a fetcher. On the other hand this bolt
 * will receive result from parser and decide whether continue crawling or not.
 *
 *
 * @author Nguyen Duc Dung
 * @since 11/29/12 11:41 PM
 *
 */
class HandlerBolt extends StormBolt(outputFields = List("handler")) with Logging {

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(StartWith(session, job)) => tuple emit Fetch(session, job)
      case Seq(Handle(session, job)) => {
        job.result.map(webPage => {
          if (!webPage.isError) {
            session.fetchedUrls.add(job.webUrl)
            session.countUrl += webPage.webUrls.size
            //Set the parent for the website.
            if (!job.parent.isEmpty) {
              val parentWebPage = job.parent.get.result.get
              webPage.parent = parentWebPage
            }

            val crawlJobs = for (webUrl <- webPage.webUrls if (shouldCrawl(session, webUrl))) yield {
              buildCrawlJob(session, new CrawlJob(webUrl, job))
            }

            //Note: We don't emit a tuple when changing the session value, to make sure all session when emit have same values.
            crawlJobs.foreach(tuple emit Fetch(session, _))
          }
        })
      }
    }
    tuple.ack
  }

  private def buildCrawlJob(session: CrawlSession, job: CrawlJob) = {
    if (job.depth > session.currentDepth) {
      session.currentDepth = job.depth
    }

    //Counting
    session.subJobCount += 1

    //Store queue url.
    session.queueUrls.add(job.webUrl)

    CrawlJob(job.webUrl)
  }

  private def shouldCrawl(session: CrawlSession, webUrl: WebUrl): Boolean = {

    val job = session.job

    //Step 1: Checking whether go for the job or not
    if (job.maxSubJob >= 0 && session.subJobCount >= job.maxSubJob) {
      return false
    }

    if (session.currentDepth > job.maxDepth && job.maxDepth > 0) {
      session.currentDepth -= 1
      return false
    }

    if (job.filterPattern.matcher(webUrl.url).matches()) return false

    //Only crawl the url is match with url regex
    if (job.urlRegex.isDefined && !SimpleRegexMatcher.matcher(webUrl.url, job.urlRegex.get)) {
      return false
    }

    //Not crawl the exclude url
    if (job.excludeUrl != null) job.excludeUrl.foreach(regex => {
      if (SimpleRegexMatcher.matcher(webUrl.url, regex)) {
        return false
      }
    })

    //Only crawl in same domain.
    if (!job.onlyCrawlInSameDomain
      || (job.onlyCrawlInSameDomain && webUrl.domainName == session.domainName)) {
      //Make sure we not fetch a link what we did already.
      if (!session.fetchedUrls.contains(webUrl)) {
        //And make sure the url is not in the queue
        if (!session.queueUrls.contains(webUrl)) {
          return true
        }
      }
    }

    false
  }

}
