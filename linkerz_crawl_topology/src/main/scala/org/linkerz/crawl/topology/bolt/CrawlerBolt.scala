package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Fetch, StartWith, Crawl}
import collection.JavaConversions._
import org.linkerz.crawler.bot.job.FeedJob

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
  def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(StartWith(job)) => {
        tuple emit Fetch(job)
      }
      case Seq(Crawl(job)) => {
        job.result.map(webPage => {
          webPage.webUrls.foreach {
            webUrl =>
              tuple emit Fetch(FeedJob(job.newFeed.copy(url = webUrl.url)))
          }
        })
      }
    }
    tuple.ack
  }
}
