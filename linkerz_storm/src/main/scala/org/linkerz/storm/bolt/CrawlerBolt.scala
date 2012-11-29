package org.linkerz.storm.bolt

import storm.scala.dsl.{StormBolt, StormSpout}
import util.Random
import backtype.storm.utils.Utils
import backtype.storm.tuple.Tuple

/**
 * The mission of this bolt will receive job from the feed spot and emit it to a fetcher. On the other hand this bolt
 * will receive result from parser and decide whether continue crawling or not.
 *
 *
 * @author Nguyen Duc Dung
 * @since 11/29/12 11:41 PM
 *
 */
class CrawlerBolt extends StormBolt(outputFields = List("url")) {
  def execute(p1: Tuple) {}
}
