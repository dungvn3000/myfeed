package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.FetchDone
import grizzled.slf4j.Logging
import java.util.UUID

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("sessionId", "event")) with Logging {

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, Handle(job)) => {

        tuple emit(sessionId, FetchDone(job))
      }
    }
    tuple.ack()
  }
}
