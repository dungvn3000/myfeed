package org.linkerz.crawl.topology

import bolt._
import backtype.storm.topology.TopologyBuilder
import spout.FeedSpout
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
    builder.setSpout("spout", new FeedSpout)
    builder.setBolt("handler", new HandlerBolt, 10).
      fieldsGrouping("spout", new Fields("sessionId")).fieldsGrouping("parser", new Fields("sessionId"))
    builder.setBolt("fetcher", new FetcherBolt, 20).shuffleGrouping("handler")
    builder.setBolt("parser", new ParserBolt, 20).shuffleGrouping("fetcher")
    builder.setBolt("metaFetcher", new MetaFetcherBolt, 10).shuffleGrouping("parser")
    builder.setBolt("persistent", new PersistentBolt, 5).shuffleGrouping("metaFetcher")
    builder.createTopology()
  }
}
