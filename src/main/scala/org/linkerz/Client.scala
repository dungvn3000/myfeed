package org.linkerz

import com.rabbitmq.client.{MessageProperties, ConnectionFactory}
import crawler.bot.job.NewFeedJob
import util.Marshal

/**
 * The Class Client.
 *
 * @author Nguyen Duc Dung
 * @since 11/4/12 8:26 PM
 *
 */
object Client extends App {

  val factory = new ConnectionFactory
  factory.setHost("localhost")

  //Send a job to server.
  val connection = factory.newConnection()
  val channel = connection.createChannel()
  channel.queueDeclare("jobQueue", true, false, false, null)

  val job1 = new NewFeedJob("http://genk.vn")
  job1.maxDepth = 1


  val job2 = new NewFeedJob("http://vnexpress.net/")
  job2.maxDepth = 1

  channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC,
    Marshal.dump(job1))

  channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC,
    Marshal.dump(job2))


  channel.close()
  connection.close()
}
