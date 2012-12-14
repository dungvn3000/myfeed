package org.linkerz.recommendation

import org.apache.commons.math3.stat.correlation.{SpearmansCorrelation, PearsonsCorrelation}
import breeze.text.tokenize.JavaWordTokenizer
import breeze.text.transform.StopWordFilter
import breeze.text.analyze.CaseFolder
import org.apache.commons.math3.stat.Frequency
import collection.immutable.HashSet
import collection.mutable.ListBuffer

/**
  * The Class PearsonsCorrelation.
 *
 * @author Nguyen Duc Dung
 * @since 11/9/12 7:40 PM
 *
 */
object Correlation {

  val pearsonsCorrelation = new PearsonsCorrelation
  val spearmansCorrelation = new SpearmansCorrelation()
  val tokenizer = JavaWordTokenizer ~> StopWordFilter("vi")

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
