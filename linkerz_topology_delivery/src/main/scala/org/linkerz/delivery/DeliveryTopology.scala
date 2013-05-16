package org.linkerz.delivery

import backtype.storm.topology.TopologyBuilder
import bolt.{ScoringBolt, GetNewsBolt}
import spout.DeliverySpout

/**
 * The Class RecommendationTopology.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 1:27 PM
 *
 */
object DeliveryTopology extends Serializable {

  def topology = {
    val builder = new TopologyBuilder
    builder.setSpout("spout", new DeliverySpout)
    builder.setBolt("getNews", new GetNewsBolt, 5).shuffleGrouping("spout")
    builder.setBolt("scoring", new ScoringBolt, 20).shuffleGrouping("getNews")
    builder.createTopology()
  }

}
