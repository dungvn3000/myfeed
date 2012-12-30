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
  //Set time out for an crawl job is 10 minutes
  conf setMessageTimeoutSecs 60 * 10

  StormSubmitter.submitTopology("crawling", conf, topology)
}
