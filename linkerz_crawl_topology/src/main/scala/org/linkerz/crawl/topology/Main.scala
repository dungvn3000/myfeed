package org.linkerz.crawl.topology

import backtype.storm.{StormSubmitter, Config}
import CrawlTopology._

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 11:04 PM
 *
 */
object Main extends App {

  val conf = new Config()
  conf setDebug false
  conf put (Config.NIMBUS_HOST, "192.168.1.100")
  conf setNumWorkers 2

  StormSubmitter.submitTopology("crawling", conf, topology)
}
