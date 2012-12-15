package org.linkerz.recommendation

import backtype.storm.topology.TopologyBuilder
import bolt.{CorrelationBolt, MergeBolt, GetClickedLinkBolt}
import spout.RecommendationSpout

/**
 * The Class RecommendationTopology.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 1:27 PM
 *
 */
object RecommendationTopology extends Serializable {

  def topology = {
    val builder = new TopologyBuilder
    builder.setSpout("spout", new RecommendationSpout)
    builder.setBolt("getClicked", new GetClickedLinkBolt).shuffleGrouping("spout")
    builder.setBolt("merge", new MergeBolt).shuffleGrouping("getClicked")
    builder.setBolt("correlation", new CorrelationBolt).shuffleGrouping("merge")
    builder.createTopology()
  }

}
