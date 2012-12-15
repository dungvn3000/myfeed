package org.linkerz.recommendation

import org.junit.Test
import backtype.storm.{Config, LocalCluster}
import backtype.storm.utils.Utils

/**
 * The Class MainFunSuite.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 4:38 AM
 *
 */
class MainFunSuite {

  @Test
  def runRecommendationTopology() {
    val localCluster = new LocalCluster

    val conf = new Config
    conf setDebug true

    localCluster.submitTopology("recommendation", conf, RecommendationTopology.topology)

    //One day.
    Utils sleep 1000 * 60 * 60 * 24

    localCluster.shutdown()
  }

}
