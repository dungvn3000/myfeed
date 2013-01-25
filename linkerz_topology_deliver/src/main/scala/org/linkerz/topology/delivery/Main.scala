package org.linkerz.topology.delivery

import backtype.storm.{StormSubmitter, Config}
import org.linkerz.core.conf.AppConfig
import org.linkerz.topology.delivery.DeliveryTopology._

/**
 * The Class Mian.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 12:52 AM
 *
 */
object Main extends App {

  val conf = new Config()
  conf setDebug false
  conf put (Config.NIMBUS_HOST, AppConfig.nimbusHost)
  conf setNumWorkers 2

  StormSubmitter.submitTopology("delivery", conf, topology)

}
