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
    builder.setSpout("feedQueue", new FeedQueueSpout(AppConfig.rabbitMqHost), 2)
    builder.setBolt("handler", new HandlerBolt, 2).
      fieldsGrouping("feedQueue", new Fields("sessionId")).fieldsGrouping("persistent", new Fields("sessionId"))
    builder.setBolt("fetcher", new FetcherBolt, 4).shuffleGrouping("handler")
    builder.setBolt("parser", new ParserBolt, 2).shuffleGrouping("fetcher")
    builder.setBolt("metaFetcher", new MetaFetcherBolt, 2).shuffleGrouping("parser")
    builder.setBolt("persistent", new PersistentBolt, 2).shuffleGrouping("metaFetcher")
    builder.createTopology()
  }
}
