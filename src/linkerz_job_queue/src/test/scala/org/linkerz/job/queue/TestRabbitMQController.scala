/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.RabbitMQController
import handler.{EchoHandler, SyncHandler}
import job.{EchoJob, SumJob}
import org.junit.Test
import com.rabbitmq.client.ConnectionFactory
import util.Marshal

/**
 * The Class TestRabbitMQController.
 *
 * @author Nguyen Duc Dung
 * @since 9/2/12 10:57 AM
 *
 */
class TestRabbitMQController {

  val factory = new ConnectionFactory

  @Test
  def testBasicJob() {
    val controller = new RabbitMQController
    controller.connectionFactory = factory
    controller.handlers = List(new EchoHandler)
    controller.start()

    //Send a job to server.
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare("jobQueue", false, false, false, null)
    channel.basicPublish("", "jobQueue", null, Marshal.dump(new EchoJob("Hello Rabbit")))
    channel.close()
    connection.close()

    Thread.sleep(1000)
    controller.stop()
  }

}
