package org.linkerz.delivery

import backtype.storm.{StormSubmitter, Config}
import org.linkerz.core.conf.AppConfig
import org.linkerz.delivery.DeliveryTopology._

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/8/12 1:49 AM
 *
 */
object Main extends App {

  val conf = new Config()
  conf setDebug false
  conf put (Config.NIMBUS_HOST, AppConfig.nimbusHost)
  conf setNumWorkers 4
  //Set time out for an crawl job is 10 minutes
  conf setMessageTimeoutSecs 60 * 10

  StormSubmitter.submitTopology("delivery", conf, topology)

}
