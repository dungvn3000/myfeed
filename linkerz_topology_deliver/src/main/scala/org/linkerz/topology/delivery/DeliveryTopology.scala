package org.linkerz.topology.delivery

import backtype.storm.topology.TopologyBuilder
import bolt.{DeliveryBolt, GetNewsBolt}
import spout.ScheduleSpout

/**
 * The Class DeliveryTopology.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 12:53 AM
 *
 */
object DeliveryTopology extends Serializable {

  def topology = {
    val builder = new TopologyBuilder
    builder.setSpout("scheduleSpout", new ScheduleSpout)
    builder.setBolt("getnews", new GetNewsBolt, 3)
    builder.setBolt("delivery", new DeliveryBolt, 3)
    builder.createTopology()
  }

}
