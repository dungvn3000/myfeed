package org.linkerz.storm

import backtype.storm.topology.TopologyBuilder
import bolt._
import org.linkerz.storm.spout._
import backtype.storm.{Config, LocalCluster}
import backtype.storm.utils.Utils

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/27/12 11:39 PM
 *
 */
object Main extends App {

  val builder = new TopologyBuilder

  builder.setSpout("feedQueue", new FeedQueueSpout)
  builder.setBolt("crawler", new CrawlerBolt).shuffleGrouping("feedQueue").shuffleGrouping("persistent")
  builder.setBolt("fetcher", new FetcherBolt).shuffleGrouping("crawler")
  builder.setBolt("parser", new ParserBolt).shuffleGrouping("fetcher")
  builder.setBolt("metaFetcher", new MetaFetcherBolt).shuffleGrouping("parser")
  builder.setBolt("persistent", new PersistentBolt).shuffleGrouping("metaFetcher")


  val localCluster = new LocalCluster

  val conf = new Config
  conf setDebug true


  localCluster.submitTopology("crawler",conf,  builder.createTopology())

  Utils sleep 20000

  localCluster.shutdown()

}
