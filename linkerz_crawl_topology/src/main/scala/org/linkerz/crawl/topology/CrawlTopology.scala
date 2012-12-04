package org.linkerz.crawl.topology

import bolt._
import org.linkerz.core.conf.AppConfig
import backtype.storm.topology.TopologyBuilder
import spout.FeedQueueSpout
import backtype.storm.tuple.Fields

/**
 * The Class CrawlTopology.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 4:00 AM
 *
 */
object CrawlTopology extends Serializable {

  def topology = {
    val builder = new TopologyBuilder
    builder.setSpout("feedQueue", new FeedQueueSpout(AppConfig.rabbitMqHost), 10)
    builder.setBolt("handler", new HandlerBolt, 5).
      fieldsGrouping("feedQueue", new Fields("sessionId")).fieldsGrouping("persistent", new Fields("sessionId"))
    builder.setBolt("fetcher", new FetcherBolt, 20).shuffleGrouping("handler")
    builder.setBolt("parser", new ParserBolt, 10).shuffleGrouping("fetcher")
    builder.setBolt("metaFetcher", new MetaFetcherBolt, 20).shuffleGrouping("parser")
    builder.setBolt("persistent", new PersistentBolt, 10).shuffleGrouping("metaFetcher")
    builder.createTopology()
  }
}
