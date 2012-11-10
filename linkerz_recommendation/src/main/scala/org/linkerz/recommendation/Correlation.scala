package org.linkerz.recommendation

import org.apache.commons.math3.stat.correlation.{SpearmansCorrelation, PearsonsCorrelation}
import org.linkerz.model.Link
import breeze.text.tokenize.JavaWordTokenizer
import breeze.text.transform.StopWordFilter
import breeze.text.analyze.CaseFolder
import org.apache.commons.math3.stat.Frequency
import collection.immutable.HashSet
import java.util
import collection.JavaConversions._
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
   * Calculate similar score between two links by using @see PearsonsCorrelation.
   * @param link1
   * @param link2
   * @return
   */
  def sim_pearson(link1: Link, link2: Link) = {
    //Step 1: Tokenize
    val words1 = tokenizer(CaseFolder(link1.title + " " + link1.description)).filter(word => word.trim.length > 1)
    val words2 = tokenizer(CaseFolder(link2.title + " " + link2.description)).filter(word => word.trim.length > 1)

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
