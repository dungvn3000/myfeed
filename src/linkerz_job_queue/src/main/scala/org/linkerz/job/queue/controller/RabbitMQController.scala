/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.controller

import com.rabbitmq.client.{QueueingConsumer, ConnectionFactory}
import util.Marshal
import org.linkerz.job.queue.core.Job
import actors.DaemonActor

/**
 * This controller will reivce job from RabbitMQ server.
 *
 * @author Nguyen Duc Dung
 * @since 9/2/12 10:35 AM
 *
 */
class RabbitMQController extends BaseController {

  var connectionFactory: ConnectionFactory = _
  var queueName = "jobQueue"

  val consumerActor = new DaemonActor() {
    def act() {
      val connection = connectionFactory.newConnection()
      val channel = connection.createChannel()
      val consumer = new QueueingConsumer(channel)
      channel.basicConsume(queueName, true, consumer)
      loop {
        val delivery = consumer.nextDelivery()
        if (delivery.getBody != null) {
          val job = Marshal.load[Job](delivery.getBody)
          handlerActor ! NEXT(job)
        }
        receive {
          case STOP => {
            reply("exit")
            exit()
          }
        }
      }
    }
  }

  override def start() {
    super.start()
    consumerActor.start()
  }

  override def stop() {
    super.stop()
    consumerActor !? STOP
  }
}
