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

  val conf = ConfigFactory.load()

  def mongoHost = conf.getString(mongoKey + defaultKey + "host")
  def mongoDb = conf.getString(mongoKey + defaultKey + "db")
  def rabbitMqHost = conf.getString(rabbitMqKey + defaultKey + "host")

  def controllerSystem = conf.getConfig("controllerSystem")

}
