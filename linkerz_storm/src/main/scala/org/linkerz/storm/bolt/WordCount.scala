package org.linkerz.storm.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import collection.mutable.{Map, HashMap}

/**
 * The Class WordCount.
 *
 * @author Nguyen Duc Dung
 * @since 11/27/12 1:02 PM
 *
 */
class WordCount extends StormBolt(outputFields = List("word", "count")){

  var counts: Map[String, Int] = _

  setup {
    counts = new HashMap[String, Int].withDefaultValue(0)
  }

  def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(word: String) => {
        counts(word) += 1
        using anchor tuple emit (word, counts(word))
        tuple ack
      }
    }
  }
}
