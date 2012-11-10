package org.linkerz

import com.rabbitmq.client.ConnectionFactory
import crawler.bot.factory.ParserPluginFactory
import crawler.bot.handler.NewFeedHandler
import crawler.core.factory.DefaultDownloadFactory
import job.queue.controller.RabbitMQController

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/2/12 3:13 PM
 *
 */
object Main extends App {

  val factory = new ConnectionFactory
  factory.setHost("localhost")

  val controller = new RabbitMQController(factory)

  controller.handlers = List(
    new NewFeedHandler(new DefaultDownloadFactory, new ParserPluginFactory)
  )

  controller.start()
}