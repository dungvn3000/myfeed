package org.linkerz.storm.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple

/**
 * The Class SplitSentence.
 *
 * @author Nguyen Duc Dung
 * @since 11/27/12 12:58 PM
 *
 */
class SplitSentence extends StormBolt(outputFields = List("word")) {
  override def execute(tuple: Tuple) {tuple matchSeq {
    case Seq(sentence: String) => sentence split " " foreach {
      word => using anchor tuple emit word
    }
    tuple ack
  }}
}
