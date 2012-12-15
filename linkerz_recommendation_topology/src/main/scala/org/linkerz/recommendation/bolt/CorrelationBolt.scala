package org.linkerz.recommendation.bolt

import storm.scala.dsl.StormBolt
import org.bson.types.ObjectId
import org.linkerz.recommendation.event.MergeLink
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation
import breeze.text.tokenize.{Tokenizer, JavaWordTokenizer}
import breeze.text.transform.StopWordFilter
import breeze.text.analyze.CaseFolder
import collection.immutable.HashSet
import org.apache.commons.math3.stat.Frequency
import collection.mutable.ListBuffer
import grizzled.slf4j.Logging

/**
 * The Class CorrelationBolt.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 3:48 AM
 *
 */
class CorrelationBolt extends StormBolt(outputFields = List("userId", "event")) with Logging {

  @transient var pearsonsCorrelation: PearsonsCorrelation = _
  @transient var tokenizer: Tokenizer = _

  setup {
    pearsonsCorrelation = new PearsonsCorrelation
    tokenizer = JavaWordTokenizer ~> StopWordFilter("vi")
  }

  execute {
    tuple => tuple matchSeq {
      case Seq(userId: ObjectId, MergeLink(clickedLink, link)) => {
        if (clickedLink.text.isDefined && link.text.isDefined) {
          val text1 = clickedLink.text.get
          val text2 = link.text.get
          val score = sim_pearson(text1, text2)
          info(score)
          tuple emit(clickedLink.id, link.id, score)
        }
      }
    }
  }

  /**
   * Calculate similar score between two texts by using @see PearsonsCorrelation.
   * @param s1
   * @param s2
   * @return
   */
  def sim_pearson(s1: String, s2: String) = {
    //Step 1: Tokenize
    val words1 = tokenizer(CaseFolder(s1)).filter(word => word.trim.length > 1)
    val words2 = tokenizer(CaseFolder(s2)).filter(word => word.trim.length > 1)

    var keys = new HashSet[String]
    //Step 2: Counting.
    val frequency1 = new Frequency()
    words1.foreach(word => {
      frequency1.addValue(word)
      keys += word
    })

    val frequency2 = new Frequency()
    words2.foreach(word => {
      frequency2.addValue(word)
      keys += word
    })

    val data1 = new ListBuffer[Double]()
    val data2 = new ListBuffer[Double]()
    keys.foreach(word => {
      data1 += frequency1.getCount(word)
      data2 += frequency2.getCount(word)
    })

    pearsonsCorrelation.correlation(data1.toArray, data2.toArray)
  }

}
