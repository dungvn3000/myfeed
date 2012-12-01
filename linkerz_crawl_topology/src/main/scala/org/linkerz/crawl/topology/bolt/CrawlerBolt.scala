package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Fetch, StartWith, Crawl}
import collection.JavaConversions._
import org.linkerz.crawler.bot.job.FeedJob
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.crawler.core.session.CrawlSession

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

  private var startJob: FeedJob = _

  private var currentSession: CrawlSession = _

  override def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(StartWith(job)) => {
        startJob = job
        tuple emit Fetch(job)
      }
      case Seq(Crawl(job)) => {
        job.result.map(webPage => {
          webPage.webUrls.foreach {
            webUrl => if (shouldCrawl(webUrl)) {
              tuple emit Fetch(FeedJob(job.newFeed.copy(url = webUrl.url)))
            }
          }
        })
      }
    }
    tuple.ack
  }

  private def shouldCrawl(url: WebUrl): Boolean = {
    true
  }

}
