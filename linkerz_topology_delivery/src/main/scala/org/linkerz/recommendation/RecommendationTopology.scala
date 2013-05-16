package org.linkerz.recommendation

import backtype.storm.topology.TopologyBuilder
import bolt.{CorrelationBolt, MergeBolt, GetUserLinkBolt}
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
    builder.setBolt("getUserLink", new GetUserLinkBolt).shuffleGrouping("spout")
    builder.setBolt("merge", new MergeBolt, 5).shuffleGrouping("getUserLink")
    builder.setBolt("correlation", new CorrelationBolt, 20).shuffleGrouping("merge")
    builder.createTopology()
  }

}
