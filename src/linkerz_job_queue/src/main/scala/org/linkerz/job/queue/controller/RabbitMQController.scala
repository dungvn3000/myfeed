/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.job.queue.controller

import com.rabbitmq.client._
import util.Marshal
import org.linkerz.job.queue.core.Job
import scalaz.Scalaz._

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

  private var _connection: Connection = _
  private var _channel: Channel = _

  val consumerActor = actor {
    (event: Event) => {
      event match {
        case START => {
          _connection = connectionFactory.newConnection()
          _channel = _connection.createChannel()
          val consumer = new QueueingConsumer(_channel)
          _channel.basicConsume(queueName, false, consumer)
          while (true) {
            val delivery = consumer.nextDelivery()
            if (delivery.getBody != null) {
              val job = Marshal.load[Job](delivery.getBody)
              handlerActor ! NEXT(job)
              _channel.basicAck(delivery.getEnvelope.getDeliveryTag, false)
            }
          }
        }
      }
    }
  }

  override def start() {
    super.start()
    consumerActor ! START
  }

  override def stop() {
    super.stop()
    _channel.close()
    _connection.close()
  }
}
