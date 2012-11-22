package org.linkerz

import com.rabbitmq.client.ConnectionFactory
import core.conf.AppConfig
import crawler.bot.factory.NewsParserFactory
import crawler.bot.handler.NewFeedHandler
import crawler.core.factory.DefaultDownloadFactory
import job.queue.controller.RabbitMQController
import recommendation.handler.RecommendHandler

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/2/12 3:13 PM
 *
 */
object Main extends App {

  val factory = new ConnectionFactory
  factory.setHost(AppConfig.rabbitMqHost)

  val controller = new RabbitMQController(factory)
  controller.prefetchCount = 1

  controller.handlers = List(
    new NewFeedHandler(new DefaultDownloadFactory, new NewsParserFactory),
    new RecommendHandler
  )

  controller.start()
}
