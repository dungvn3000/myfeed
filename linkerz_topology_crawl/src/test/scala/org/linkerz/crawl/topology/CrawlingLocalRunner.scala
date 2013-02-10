package org.linkerz.crawl.topology

import backtype.storm.{Config, LocalCluster}
import backtype.storm.utils.Utils
import CrawlTopology._

/**
 * The Class MainFunSuite.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:59 AM
 *
 */
object CrawlingLocalRunner extends App {

  val localCluster = new LocalCluster

  val conf = new Config
  conf setDebug false
  conf setNumWorkers 4
  //Set time out for an crawl job is 10 minutes
  conf setMessageTimeoutSecs 60 * 10

  localCluster.submitTopology("crawler", conf, topology)

  //One day.
  Utils sleep 1000 * 60 * 60 * 24

  localCluster.shutdown()

}
