package org.linkerz.recommendation

import backtype.storm.topology.TopologyBuilder

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

    builder.createTopology()
  }

}
