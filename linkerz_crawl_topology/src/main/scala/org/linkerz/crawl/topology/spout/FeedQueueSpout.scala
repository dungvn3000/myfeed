package org.linkerz.crawl.topology.spout

import org.linkerz.crawl.topology.job.CrawlJob
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.event._
import java.util.UUID
import storm.scala.rabbitmq.RabbitMqSpout

/**
 * This spout is using for take a feeding job form the RabbitMq server.
 * After that it is gonna emit to the crawling bolt and waiting for it before take another job.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:42 AM
 *
 */
class FeedQueueSpout(rabbitMqHost: String)
  extends RabbitMqSpout(queueName = "feedQueue", rabbitMqHost = rabbitMqHost, outputFields = List("sessionId", "event")) with Logging {

  def nextDelivery(job: AnyRef) {
    job match {
      case job: CrawlJob => {
        //Make sure the id is unique all the time.
        val sessionId = UUID.randomUUID()
        using msgId sessionId emit(sessionId, Start(job))
      }
      case _ => //Ignore
    }
  }

  override def ack(msgId: Any) {
    super.ack(msgId)
    this emit(msgId.asInstanceOf[UUID], Ack)
  }

  override def fail(msgId: Any) {
    super.fail(msgId)
    this emit(msgId.asInstanceOf[UUID], Fail)
  }
}
