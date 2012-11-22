package org.linkerz.core.actor

import com.typesafe.config.ConfigFactory

/**
 * The Class Config.
 *
 * @author Nguyen Duc Dung
 * @since 11/21/12 12:22 AM
 *
 */
object AppConfig {

  val defaultKey = "default."
  val mongoKey = "mongodb."
  val rabbitMqKey = "rabbitmq."

  lazy val conf = ConfigFactory.load()

  lazy val mongoHost = conf.getString(mongoKey + defaultKey + "host")
  lazy val mongoDb = conf.getString(mongoKey + defaultKey + "db")
  lazy val rabbitMqHost = conf.getString(rabbitMqKey + defaultKey + "host")

  lazy val controllerSystem = conf.getConfig("controllerSystem")

}
