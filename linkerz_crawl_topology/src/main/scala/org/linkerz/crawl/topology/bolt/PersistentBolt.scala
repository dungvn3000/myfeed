package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Crawl, Persistent}

/**
 * This bolt is using for persistent data to the database server.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:12 AM
 *
 */
class PersistentBolt extends StormBolt(outputFields = List("persistent")) {
  override def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(Persistent(job)) => {
        tuple emit Crawl(job)
      }
    }
    tuple.ack
  }
}
