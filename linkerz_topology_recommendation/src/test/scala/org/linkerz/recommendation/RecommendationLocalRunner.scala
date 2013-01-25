package org.linkerz.recommendation

import backtype.storm.{Config, LocalCluster}
import backtype.storm.utils.Utils
import org.linkerz.recommendation.RecommendationTopology._

/**
 * The Class MainFunSuite.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 4:38 AM
 *
 */
object RecommendationLocalRunner extends App {

  val localCluster = new LocalCluster

  val conf = new Config
  conf setDebug false
  conf setNumWorkers 4

  localCluster.submitTopology("recommendation", conf, topology)

  //One day.
  Utils sleep 1000 * 60 * 60 * 24

  localCluster.shutdown()

}
