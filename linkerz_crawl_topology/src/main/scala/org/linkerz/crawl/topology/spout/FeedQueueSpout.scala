package org.linkerz.crawl.topology.spout

import storm.scala.dsl.StormSpout
import com.rabbitmq.client.{ConnectionFactory, QueueingConsumer, Channel, Connection}
import util.Marshal
import com.rabbitmq.client.QueueingConsumer.Delivery
import org.linkerz.crawler.bot.job.FeedJob
import scala.{transient, Some}
import org.linkerz.crawl.topology.event.StartWith

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
  private var connection: Connection = _

  @transient
  private var channel: Channel = _

  @transient
  private var consumer: QueueingConsumer = _

  @transient
  private var currentDelivery: Option[Delivery] = None

  setup {
    val factory = new ConnectionFactory
    factory.setHost(rabbitMqHost)
    connection = factory.newConnection()
    channel = connection.createChannel()
    consumer = new QueueingConsumer(channel)

    channel.basicQos(prefetchCount)
    channel.basicConsume(queueName, false, consumer)
  }


  def nextTuple() {
    try {
      val delivery = consumer.nextDelivery(deliverTimeOut)
      if (delivery != null && delivery.getBody != null) {
        currentDelivery = Some(delivery)
        val job = Marshal.load[FeedJob](delivery.getBody)

        //Using url for tuple id, assume url is unique for each jobs.
        using msgId job.webUrl.url emit StartWith(job)
      }
    } catch {
      case ex: Exception => _collector.reportError(ex)
    }
  }

  override def ack(msgId: Any) {
    if (currentDelivery.isDefined) {
      channel.basicAck(currentDelivery.get.getEnvelope.getDeliveryTag, false)
      currentDelivery = None
    }
  }

  override def fail(msgId: Any) {
    if (currentDelivery.isDefined) {
      channel.basicReject(currentDelivery.get.getEnvelope.getDeliveryTag, true)
      currentDelivery = None
    }
  }

  override def close() {
    if (currentDelivery.isDefined) {
      channel.basicCancel(consumer.getConsumerTag)
    }
    channel.close()
    connection.close()
  }
}
