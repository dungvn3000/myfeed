package org.linkerz.crawl.topology

import backtype.storm.topology.TopologyBuilder
import bolt._
import spout.FeedSpout

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
    builder.setSpout("feedSpout", new FeedSpout)

    builder.setBolt("feedHandler", new FeedHandlerBolt, 5).shuffleGrouping("feedSpout")

    builder.setBolt("webPageFetcher", new WebPageFetcherBolt, 10).shuffleGrouping("feedHandler")

    builder.setBolt("webPageParser", new WebPageParserBolt, 5).shuffleGrouping("webPageFetcher")

    builder.setBolt("metaFetcher", new MetaFetcherBolt, 5).shuffleGrouping("webPageParser")

    builder.setBolt("persistent", new PersistentBolt, 2).shuffleGrouping("metaFetcher")

    builder.createTopology()
  }
}
