package org.linkerz.core.conf

import com.typesafe.config.ConfigFactory

/**
 * The Class Config.
 *
 * @author Nguyen Duc Dung
 * @since 11/21/12 12:22 AM
 *
 */
object AppConfig {

  private val devKey = "dev."
  private val prodKey = "prod."
  private val mongoKey = "mongodb."
  private val storm = "storm."

  lazy val conf = ConfigFactory.load()

  lazy val mongoUri = conf.getString(mongoKey + devKey + "uri")
  lazy val mongoDb = conf.getString(mongoKey + devKey + "db")
  lazy val nimbusHost = conf.getString(storm + devKey + "nimbus.host")
}
