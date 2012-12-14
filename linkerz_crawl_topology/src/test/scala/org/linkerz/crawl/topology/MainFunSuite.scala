package org.linkerz.crawl.topology

import org.scalatest.FunSuite
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
class MainFunSuite extends FunSuite {

  test("Example for Crawl Topology") {

    val localCluster = new LocalCluster

    val conf = new Config
    conf setDebug false
    conf setNumWorkers 8
    //Set time out for an crawl job is fifty minutes
    conf setMessageTimeoutSecs 60 * 15

    localCluster.submitTopology("crawler", conf, topology)

    //One day.
    Utils sleep 1000 * 60 * 60 * 24

    localCluster.shutdown()
  }

}
