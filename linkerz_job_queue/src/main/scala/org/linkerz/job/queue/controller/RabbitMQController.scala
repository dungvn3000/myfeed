/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.controller

import com.rabbitmq.client._
import util.Marshal
import org.linkerz.job.queue.core.Job
import reflect.BeanProperty
import akka.actor.{Props, Actor}
import org.linkerz.job.queue.core.Controller._

/**
 * This controller will reivce job from RabbitMQ server.
 *
 * @author Nguyen Duc Dung
 * @since 9/2/12 10:35 AM
 *
 */
class RabbitMQController(connectionFactory: ConnectionFactory) extends BaseController {

  var queueName = "jobQueue"

  //The number of job the controller will take at a time.
  var prefetchCount = 10

  //Time out for waiting a delivery.
  var deliverTimeOut = 1000

  private var _connection: Connection = _
  private var _channel: Channel = _

  private var _isStop = false

  val consumerActor = systemActor.actorOf(Props(new Actor {
    protected def receive = {
      case "start" => {
        _connection = connectionFactory.newConnection()
        _channel = _connection.createChannel()
        _channel.basicQos(prefetchCount)
        val consumer = new QueueingConsumer(_channel)
        _channel.basicConsume(queueName, false, consumer)
        var job: Job = null
        try {
          while (!_isStop) {
            val delivery = consumer.nextDelivery(deliverTimeOut)
            if (delivery != null && delivery.getBody != null) {
              job = Marshal.load[Job](delivery.getBody)
              RabbitMQController.this ? job
              _channel.basicAck(delivery.getEnvelope.getDeliveryTag, false)
            }
          }
        } catch {
          case ex: Exception => handleError(job, ex)
        }
      }
      case "stop" => context.stop(self)
    }
  }))

  override def start() {
    super.start()
    consumerActor ! "start"
  }

  override def stop() {
    _isStop = true
    consumerActor ! "stop"
    while (!consumerActor.isTerminated) Thread.sleep(1000)
    if (_channel != null &&  _channel.isOpen) {
      _channel.close()
    }
    if(_connection != null) _connection.close()
    super.stop()
  }
}
