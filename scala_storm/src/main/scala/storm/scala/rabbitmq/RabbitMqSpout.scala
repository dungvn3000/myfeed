package storm.scala.rabbitmq

import storm.scala.dsl.StormSpout
import com.rabbitmq.client.{ConnectionFactory, QueueingConsumer, Channel, Connection}
import com.rabbitmq.client.QueueingConsumer.Delivery
import util.Marshal

/**
 * The Class RabbitMqSpout.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 3:07 AM
 *
 */
abstract class RabbitMqSpout(queueName: String, rabbitMqHost: String, prefetchCount: Int = 1, deliverTimeOut: Int = 5000, outputFields: List[String]) extends StormSpout(outputFields = outputFields) {

  @transient
  protected var connection: Option[Connection] = None

  @transient
  protected var channel: Option[Channel] = None

  @transient
  protected var consumer: Option[QueueingConsumer] = None

  @transient
  protected var currentDelivery: Option[Delivery] = None

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
        _consumer => val delivery = _consumer.nextDelivery(deliverTimeOut)
        if (delivery != null && delivery.getBody != null) {
          currentDelivery = Some(delivery)
          _nextDelivery(Marshal.load[AnyRef](delivery.getBody))
        }
      }
    } catch {
      case ex: Exception => _collector.reportError(ex)
    }
  }

  var _nextDelivery: AnyRef => Unit = _

  /**
   * This method will be called when a delivery come from the rabbit queue.
   */
  def nextDelivery(func: AnyRef => Unit) {
    _nextDelivery = func
  }


  override def ack(msgId: Any) {
    currentDelivery.map {
      _delivery => channel.map(_.basicAck(_delivery.getEnvelope.getDeliveryTag, false))
    }
  }

  override def fail(msgId: Any) {
    currentDelivery.map {
      _delivery => channel.map(_.basicReject(_delivery.getEnvelope.getDeliveryTag, true))
    }
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
