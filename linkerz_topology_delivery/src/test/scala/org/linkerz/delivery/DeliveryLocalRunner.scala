package org.linkerz.delivery

import backtype.storm.{Config, LocalCluster}
import backtype.storm.utils.Utils
import org.linkerz.delivery.DeliveryTopology._

/**
 * The Class MainFunSuite.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 4:38 AM
 *
 */
object DeliveryLocalRunner extends App {

  val localCluster = new LocalCluster

  val conf = new Config
  conf setDebug false
  conf setNumWorkers 4
  //Set time out for an crawl job is 10 minutes
  conf setMessageTimeoutSecs 60 * 10

  localCluster.submitTopology("delivery", conf, topology)

  //One day.
  Utils sleep 1000 * 60 * 60 * 24

  localCluster.shutdown()

}
