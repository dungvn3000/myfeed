package org.linkerz.crawl.topology

import bolt._
import org.linkerz.core.conf.AppConfig
import backtype.storm.topology.TopologyBuilder
import spout.FeedQueueSpout

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
    builder.setSpout("feedQueue", new FeedQueueSpout(AppConfig.rabbitMqHost))
    builder.setBolt("crawler", new CrawlerBolt).shuffleGrouping("feedQueue").shuffleGrouping("persistent")
    builder.setBolt("fetcher", new FetcherBolt).shuffleGrouping("crawler")
    builder.setBolt("parser", new ParserBolt).shuffleGrouping("fetcher")
    builder.setBolt("metaFetcher", new MetaFetcherBolt).shuffleGrouping("parser")
    builder.setBolt("persistent", new PersistentBolt).shuffleGrouping("metaFetcher")
    builder.createTopology()
  }
}
