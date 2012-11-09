package org.linkerz.recommendation

import org.apache.commons.math3.stat.correlation.{SpearmansCorrelation, PearsonsCorrelation}

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

  def sim_pearson(v1: Map[String, String], v2: Map[String, String]): Double = {
    //Step 1: Combine keys of two maps.
    val keys = v1.keys ++ v2.keys

    if (keys.size == 0) return 0

    //Step 2: Build data
    val data1 = keys.toArray.map(key => {
      v1.isDefinedAt(key) match {
        case true => v1(key).toDouble
        case false => 0.0
      }
    })

    val data2 = keys.toArray.map(key => {
      v2.isDefinedAt(key) match {
        case true => v2(key).toDouble
        case false => 0.0
      }
    })

    pearsonsCorrelation.correlation(data1, data2)
  }

}
