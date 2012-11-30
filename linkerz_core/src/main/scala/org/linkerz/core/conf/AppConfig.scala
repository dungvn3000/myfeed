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

  private val defaultKey = "default."
  private val mongoKey = "mongodb."
  private val rabbitMqKey = "rabbitmq."

  lazy val conf = ConfigFactory.load()

  lazy val mongoHost = conf.getString(mongoKey + defaultKey + "host")
  lazy val mongoDb = conf.getString(mongoKey + defaultKey + "db")
  lazy val rabbitMqHost = conf.getString(rabbitMqKey + defaultKey + "host")

  lazy val controllerSystem = conf.getConfig("controllerSystem")

  lazy val monitorActorPath = conf.getString("server.monitor.path")
}
