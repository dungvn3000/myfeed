package vn.myfeed.crawl.topology

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

    builder.setBolt("fetcher", new FetcherBolt, 10).shuffleGrouping("spout")

    builder.setBolt("downloader", new DownloadBolt, 20).fieldsGrouping("fetcher", new Fields("feedId"))

    builder.setBolt("parser", new ParserBolt, 20).shuffleGrouping("downloader")

    builder.setBolt("persistent", new PersistentBolt, 2).shuffleGrouping("parser")

    builder.setBolt("delivery", new DeliveryBolt, 10).shuffleGrouping("persistent")

    builder.createTopology()
  }
}
