package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.StartWith

/**
 * The mission of this bolt will receive job from the feed spot and emit it to a fetcher. On the other hand this bolt
 * will receive result from parser and decide whether continue crawling or not.
 *
 *
 * @author Nguyen Duc Dung
 * @since 11/29/12 11:41 PM
 *
 */
class CrawlerBolt extends StormBolt(outputFields = List("feedJob")) {
  def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(StartWith(feedJob)) => {
        println(feedJob.webUrl.url)
      }
    }
  }
}
