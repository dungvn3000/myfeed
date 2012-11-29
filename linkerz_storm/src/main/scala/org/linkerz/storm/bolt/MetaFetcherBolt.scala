package org.linkerz.storm.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("feedJob")){
  def execute(p1: Tuple) {}
}
