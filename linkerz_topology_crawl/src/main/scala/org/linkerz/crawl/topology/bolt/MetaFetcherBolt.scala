package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{Parse, MetaFetch}
import java.util.UUID

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("sessionId", "event")) {

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, Parse(job)) => {
        tuple emit(sessionId, MetaFetch(job))
      }
    }
    tuple.ack()
  }
}
