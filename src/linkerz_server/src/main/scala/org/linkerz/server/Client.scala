/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.server

import com.rabbitmq.client.{MessageProperties, ConnectionFactory}
import org.linkerz.crawler.core.job.CrawlJob
import util.Marshal

/**
 * The Class Client.
 *
 * @author Nguyen Duc Dung
 * @since 9/4/12 1:03 PM
 *
 */
object Client extends App {
  val factory = new ConnectionFactory
  factory.setHost("localhost")
  val connection = factory.newConnection()
  val channel = connection.createChannel()
  channel.queueDeclare("jobQueue", true, false, false, null)

  val job1 = new CrawlJob("http://news.zing.vn/")
  val job2 = new CrawlJob("http://vnexpress.net/")

  channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC, Marshal.dump(job1))
//  channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC, Marshal.dump(job2))

  channel.close()
  connection.close()
}
