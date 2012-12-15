package org.linkerz.recommendation

import backtype.storm.topology.TopologyBuilder
import bolt.{GetLinkBolt, GetUserBolt}
import spout.RecommendQueueSpout
import org.linkerz.core.conf.AppConfig

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
    builder.setSpout("recommmedQueue", new RecommendQueueSpout(AppConfig.rabbitMqHost))
    builder.setBolt("user", new GetUserBolt).shuffleGrouping("recommmedQueue")
    builder.setBolt("link", new GetLinkBolt).shuffleGrouping("recommmedQueue")
    builder.createTopology()
  }

}
