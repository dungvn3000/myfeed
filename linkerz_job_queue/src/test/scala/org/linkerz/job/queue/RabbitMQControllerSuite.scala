/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue

import controller.RabbitMQController
import handler.{AsyncTestHandler, EchoHandler}
import job.{EmptyJob, EchoJob}
import com.rabbitmq.client.{MessageProperties, ConnectionFactory}
import util.Marshal
import org.scalatest.FunSuite

/**
 * The Class TestRabbitMQController.
 *
 * @author Nguyen Duc Dung
 * @since 9/2/12 10:57 AM
 *
 */
class RabbitMQControllerSuite extends FunSuite {

  val factory = new ConnectionFactory
  factory.setHost("localhost")

  test("test basic job") {
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

  test("test with 1000 job") {
    val controller = new RabbitMQController
    controller.connectionFactory = factory
    controller.handlers = List(new EchoHandler)
    controller.start()

    //Send a job to server.
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare("jobQueue", false, false, true, null)

    for (i <- 0 to 999) {
      channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC,
        Marshal.dump(new EchoJob("Hello Rabbit " + i)))
    }

    channel.close()
    connection.close()

    //Waiting for the controller.
    Thread.sleep(3000)
    controller.stop()
  }

  test("test with 2 controllers") {
    //Controller 1
    val controller1 = new RabbitMQController
    controller1.connectionFactory = factory
    controller1.handlers = List(new EchoHandler, new AsyncTestHandler)
    controller1.start()

    //Controller 2
    val controller2 = new RabbitMQController
    controller2.connectionFactory = factory
    controller2.handlers = List(new EchoHandler, new AsyncTestHandler)
    controller2.start()

    //Send a job to server.
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    val queue = channel.queueDeclare("jobQueue", false, false, true, null)

    for (i <- 0 to 9) {
      channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC,
        Marshal.dump(new EchoJob("Hello Rabbit " + i)))
      val emptyJob = new EmptyJob()
      emptyJob.maxSubJob = 10
      channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC,
        Marshal.dump(emptyJob))
    }

    channel.close()
    connection.close()

    Thread.sleep(30000)

    controller1.stop()
    controller2.stop()
  }

}
