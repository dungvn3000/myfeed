package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.Parse

/**
 * This bolt will parse a web page.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:00 AM
 *
 */
class ParserBolt extends StormBolt(outputFields = List("parse")) {
  def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(Parse(feedJob)) => {

      }
    }
    tuple.ack
  }
}
