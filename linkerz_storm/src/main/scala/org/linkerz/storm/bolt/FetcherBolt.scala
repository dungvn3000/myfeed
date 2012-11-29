package org.linkerz.storm.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("html")) {

  def execute(tuple: Tuple) {

  }
}
