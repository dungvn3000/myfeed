package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{Handle, Persistent}

/**
 * This bolt is using for persistent data to the database server.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:12 AM
 *
 */
class PersistentBolt extends StormBolt(outputFields = List("persistent")) {
  execute {
    tuple => tuple matchSeq {
      case Seq(Persistent(sessionId, job)) => {
        tuple emit Handle(sessionId, job)
      }
    }
    tuple.ack
  }
}
