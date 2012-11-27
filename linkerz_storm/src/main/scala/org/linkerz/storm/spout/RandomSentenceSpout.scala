package org.linkerz.storm.spout

import storm.scala.dsl.StormSpout
import backtype.storm.utils.Utils
import util.Random

/**
 * The Class RandomSentenceSpout.
 *
 * @author Nguyen Duc Dung
 * @since 11/27/12 12:53 PM
 *
 */
class RandomSentenceSpout extends StormSpout(outputFields = List("sentence")) {

  val sentences = List("the cow jumped over the moon",
    "an apple a day keeps the doctor away",
    "four score and seven years ago",
    "snow white and the seven dwarfs",
    "i am at two with nature")

  override def nextTuple() {
    Utils.sleep(100)
    emit(sentences(Random.nextInt(sentences.length)))
  }
}
