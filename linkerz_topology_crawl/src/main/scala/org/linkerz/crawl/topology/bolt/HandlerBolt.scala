package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import java.util.UUID
import org.linkerz.crawl.topology.session.CrawlSession
import org.linkerz.crawl.topology.event.Start

/**
 * The mission of this bolt will receive job from the feed spot and emit it to a fetcher. On the other hand this bolt
 * will receive result from parser and decide whether continue crawling or not.
 *
 *
 * @author Nguyen Duc Dung
 * @since 11/29/12 11:41 PM
 *
 */
class HandlerBolt extends StormBolt(outputFields = List("sessionId", "event")) with Logging {

  private var sessions: List[CrawlSession] = Nil

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, Start(parentJob)) => {

      }
    }
  }


}
