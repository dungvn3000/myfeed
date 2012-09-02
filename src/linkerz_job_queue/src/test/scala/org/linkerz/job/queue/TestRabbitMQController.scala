/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue

import controller.RabbitMQController
import handler.EchoHandler
import job.EchoJob
import org.junit.Test
import com.rabbitmq.client.{MessageProperties, ConnectionFactory}
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
  factory.setHost("localhost")

  @Test
  def testBasicJob() {
    val controller = new RabbitMQController
    controller.connectionFactory = factory
    controller.handlers = List(new EchoHandler)
    controller.start()

    //Send a job to server.
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare("jobQueue", false, false, true, null)
    channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC,
      Marshal.dump(new EchoJob("Hello Rabbit")))
    channel.close()
    connection.close()

    Thread.sleep(1000)
    controller.stop()
  }

  @Test
  def testWith1000Job() {
    val controller = new RabbitMQController
    controller.connectionFactory = factory
    controller.handlers = List(new EchoHandler)
    controller.start()

    //Send a job to server.
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare("jobQueue", false, false, true, null)

    for (i <-0 to 999) {
      channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC,
        Marshal.dump(new EchoJob("Hello Rabbit " + i)))
    }

    channel.close()
    connection.close()

    //Waiting for the controller.
    Thread.sleep(3000)
    controller.stop()
  }

}
