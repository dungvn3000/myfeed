package org.linkerz.crawl.topology

import backtype.storm.topology.TopologyBuilder
import bolt._
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

    builder.setBolt("handler", new FeedHandlerBolt, 5)
      .fieldsGrouping("spout", new Fields("sessionId"))
      .fieldsGrouping("parser", new Fields("sessionId"))

    builder.setBolt("fetcher", new FetcherBolt, 10).fieldsGrouping("handler", new Fields("sessionId"))

    builder.setBolt("parser", new ParserBolt, 5).shuffleGrouping("fetcher")

    builder.setBolt("metaFetcher", new MetaFetcherBolt, 5).fieldsGrouping("parser", new Fields("sessionId"))

    builder.setBolt("persistent", new PersistentBolt, 2).shuffleGrouping("metaFetcher")

    builder.createTopology()
  }
}
