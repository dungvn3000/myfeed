package org.linkerz.recommendation

import backtype.storm.{StormSubmitter, Config}
import org.linkerz.core.conf.AppConfig
import org.linkerz.recommendation.RecommendationTopology._

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

  StormSubmitter.submitTopology("crawling", conf, topology)

}
