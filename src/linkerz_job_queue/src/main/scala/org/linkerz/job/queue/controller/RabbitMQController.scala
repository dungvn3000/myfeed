/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.job.queue.controller

import com.rabbitmq.client._
import util.Marshal
import org.linkerz.job.queue.core.Job
import scalaz.Scalaz._
import java.util.concurrent.{TimeUnit, Executors}
import scalaz.concurrent.Strategy
import reflect.BeanProperty

/**
 * This controller will reivce job from RabbitMQ server.
 *
 * @author Nguyen Duc Dung
 * @since 9/2/12 10:35 AM
 *
 */
class RabbitMQController extends BaseController {

  @BeanProperty
  var connectionFactory: ConnectionFactory = _

  @BeanProperty
  var queueName = "jobQueue"

  //The number of job the controller will take at a time.
  @BeanProperty
  var prefetchCount = 10

  //Time out for waiting a delivery.
  @BeanProperty
  var deliverTimeOut = 1000

  private var _connection: Connection = _
  private var _channel: Channel = _

  private var _isStop = false

  private implicit val _threadPool = Executors.newSingleThreadExecutor()
  private implicit val _strategy = Strategy.Executor

//  val consumerActor = actor {
//    (event: Event) => {
//      event match {
//        case START => {
//          _connection = connectionFactory.newConnection()
//          _channel = _connection.createChannel()
//          _channel.basicQos(prefetchCount)
//          val consumer = new QueueingConsumer(_channel)
//          _channel.basicConsume(queueName, false, consumer)
//          var job: Job = null
//          try {
//            while (!_isStop) {
//              val delivery = consumer.nextDelivery(deliverTimeOut)
//              if (delivery != null && delivery.getBody != null) {
//                job = Marshal.load[Job](delivery.getBody)
//                handlerActor !? NEXT(job)
//                _channel.basicAck(delivery.getEnvelope.getDeliveryTag, false)
//              }
//            }
//          } catch {
//            case ex: Exception => handleError(job, ex)
//          }
//        }
//      }
//    }
//  }

  override def start() {
    super.start()
//    consumerActor ! START
  }

  override def stop() {
    super.stop()
    _isStop = true
    _threadPool.shutdown()
    _threadPool.awaitTermination(60, TimeUnit.SECONDS)
    if (_channel.isOpen) {
      _channel.close()
    }
    _connection.close()
  }
}
