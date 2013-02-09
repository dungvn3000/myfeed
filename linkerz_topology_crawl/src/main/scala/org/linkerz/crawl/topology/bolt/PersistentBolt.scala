package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{MetaFetch, Persistent}
import java.util.UUID
import grizzled.slf4j.Logging

/**
 * This bolt is using for persistent data to the database server.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:12 AM
 *
 */
class PersistentBolt extends StormBolt(outputFields = List("sessionId", "event")) with Logging {
  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, MetaFetch(job)) => {


        tuple emit(sessionId, Persistent(job))
      }
    }
    tuple.ack()
  }
}
