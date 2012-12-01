package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Fetch, StartWith, Crawl}
import collection.JavaConversions._
import org.linkerz.crawl.topology.model.WebUrl
import org.linkerz.crawl.topology.session.CrawlSession
import org.linkerz.crawl.topology.job.CrawlJob

/**
 * The mission of this bolt will receive job from the feed spot and emit it to a fetcher. On the other hand this bolt
 * will receive result from parser and decide whether continue crawling or not.
 *
 *
 * @author Nguyen Duc Dung
 * @since 11/29/12 11:41 PM
 *
 */
class CrawlerBolt extends StormBolt(outputFields = List("crawl")) {

  private var currentSession: CrawlSession = _

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(StartWith(session, job)) => {
        //Begin new session
        currentSession = new CrawlSession
        currentSession.openSession(job)
        tuple emit Fetch(session, job)
      }
      case Seq(Crawl(session, job)) => {
        job.result.map(webPage => {
          webPage.webUrls.foreach {
            webUrl => if (shouldCrawl(webUrl)) {
              crawl(session, new CrawlJob(webUrl, job))
            }
          }
        })
      }
    }
    tuple.ack
  }

  private def crawl(session: CrawlSession, job: CrawlJob)(implicit tuple: Tuple) {
    if (job.depth > session.currentDepth) {
      session.currentDepth = job.depth
    }

    tuple emit Fetch(currentSession, CrawlJob(job.webUrl))

    //Counting
    session.subJobCount += 1
  }

  private def shouldCrawl(url: WebUrl): Boolean = {

    //Step 1: Checking whether go for the job or not
    if (currentSession.job.maxSubJob >= 0 && currentSession.subJobCount >= currentSession.job.maxSubJob) {
      return false
    }

    if (currentSession.currentDepth > currentSession.job.maxDepth && currentSession.job.maxDepth > 0) {
      currentSession.currentDepth -= 1
      return false
    }

    true
  }

}
