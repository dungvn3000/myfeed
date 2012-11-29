package org.linkerz.crawl.topology.bolt

import backtype.storm.topology.base.BaseBasicBolt
import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple

/**
 * This bolt will parse a web page.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:00 AM
 *
 */
class ParserBolt extends StormBolt(outputFields = List("urls")) {
  def execute(tuple: Tuple) {

  }
}
