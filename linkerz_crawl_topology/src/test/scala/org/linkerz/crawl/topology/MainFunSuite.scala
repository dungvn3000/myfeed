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
    conf setDebug true

    localCluster.submitTopology("crawler", conf, topology)

    Utils sleep 20000

    localCluster.shutdown()
  }

}
