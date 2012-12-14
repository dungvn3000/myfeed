package org.linkerz.recommendation.spout

import grizzled.slf4j.Logging
import storm.scala.rabbitmq.RabbitMqSpout
import org.linkerz.recommendation.job.RecommendJob

/**
 * The Class RecommendQueueSpout.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 2:27 AM
 *
 */
class RecommendQueueSpout(rabbitMqHost: String)
  extends RabbitMqSpout(queueName = "recommendationQueue", rabbitMqHost = rabbitMqHost, outputFields = List("event")) with Logging {

  def nextDelivery(job: AnyRef) {
    job match {
      case job: RecommendJob =>
      case _ => //ignore
    }
  }

}
