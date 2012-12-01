package org.linkerz.crawl.topology.spout

import storm.scala.dsl.StormSpout
import com.rabbitmq.client.{ConnectionFactory, QueueingConsumer, Channel, Connection}
import util.Marshal
import com.rabbitmq.client.QueueingConsumer.Delivery
import org.linkerz.crawl.topology.job.CrawlJob
import scala.{transient, Some}
import org.linkerz.crawl.topology.event.StartWith
import org.linkerz.crawl.topology.session.CrawlSession

/**
 * This spout is using for take a feeding job form the RabbitMq server.
 * After that it is gonna emit to the crawling bolt and waiting for it before take another job.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:42 AM
 *
 */
class FeedQueueSpout(rabbitMqHost: String, prefetchCount: Int = 1, deliverTimeOut: Int = 5000) extends StormSpout(outputFields = List("startWith")) {

  private val queueName = "feedQueue"

  @transient
  private var connection: Option[Connection] = None

  @transient
  private var channel: Option[Channel] = None

  @transient
  private var consumer: Option[QueueingConsumer] = None

  @transient
  private var currentDelivery: Option[Delivery] = None

  private var currentSession: CrawlSession = _

  setup {
    val factory = new ConnectionFactory
    factory.setHost(rabbitMqHost)
    val _connection = factory.newConnection()

    if (_connection != null) {
      connection = Some(_connection)
      channel = Some(_connection.createChannel())
      consumer = Some(new QueueingConsumer(channel.get))
      channel.get basicQos prefetchCount
      channel.get basicConsume(queueName, false, consumer.get)
    }
  }

  def nextTuple() {
    try {
      consumer.map {
        _consumer =>
          val delivery = _consumer.nextDelivery(deliverTimeOut)
          if (delivery != null && delivery.getBody != null) {
            currentDelivery = Some(delivery)
            Marshal.load[AnyRef](delivery.getBody) match {
              case job: CrawlJob => {
                //Begin new session
                currentSession = new CrawlSession
                currentSession.openSession(job)
                //Using url for tuple id, assume url is unique for each jobs.
                using msgId job.webUrl.url emit StartWith(currentSession, job)
              }
              case _ => //Ignore
            }
          }
      }
    } catch {
      case ex: Exception => _collector.reportError(ex)
    }
  }

  override def ack(msgId: Any) {
    currentDelivery.map {
      _delivery => channel.map(_.basicAck(_delivery.getEnvelope.getDeliveryTag, false))
    }
    currentSession.endSession()
  }

  override def fail(msgId: Any) {
    currentDelivery.map {
      _delivery => channel.map(_.basicReject(_delivery.getEnvelope.getDeliveryTag, true))
    }
    currentSession.endSession()
  }

  override def close() {
    channel.map(_channel => {
      consumer.map {
        _consumer => {
          _channel.basicCancel(_consumer.getConsumerTag)
          _channel.close()
          connection.map(_.close())
        }
      }
    })
  }
}
