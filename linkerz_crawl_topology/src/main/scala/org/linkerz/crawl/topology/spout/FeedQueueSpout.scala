package org.linkerz.crawl.topology.spout

import storm.scala.dsl.StormSpout

/**
 * This spout is using for take a feeding job form the RabbitMq server.
 * After that it is gonna emit to the crawling bolt and waiting for it before take another job.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:42 AM
 *
 */
class FeedQueueSpout extends StormSpout(outputFields = List("feedJob")) {
  def nextTuple() {}
}
