package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple

/**
 * This bolt is using for persistent data to the database server.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:12 AM
 *
 */
class PersistentBolt extends StormBolt(outputFields = List("persistent")) {
  def execute(p1: Tuple) {}
}
