package org.linkerz.topology.delivery

import backtype.storm.{Config, LocalCluster}
import org.linkerz.topology.delivery.DeliveryTopology._
import backtype.storm.utils.Utils

/**
 * The Class DeliveryLocalRunner.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 3:44 PM
 *
 */
object DeliveryLocalRunner extends App {

  val localCluster = new LocalCluster

  val conf = new Config
  conf setDebug false
  conf setNumWorkers 2

  localCluster.submitTopology("delivery", conf, topology)

  //One day.
  Utils sleep 1000 * 60 * 60 * 24

  localCluster.shutdown()

}
