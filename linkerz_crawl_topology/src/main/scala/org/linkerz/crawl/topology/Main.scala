package org.linkerz.crawl.topology

import backtype.storm.{StormSubmitter, Config}
import CrawlTopology._
import org.linkerz.core.conf.AppConfig

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
  conf put (Config.NIMBUS_HOST, AppConfig.nimbusHost)
  conf setNumWorkers 8

  StormSubmitter.submitTopology("crawling", conf, topology)
}
