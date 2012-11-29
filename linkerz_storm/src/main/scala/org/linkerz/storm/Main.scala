package org.linkerz.storm

import backtype.storm.topology.TopologyBuilder
import bolt.{WordCount, SplitSentence}
import spout.RandomSentenceSpout
import backtype.storm.tuple.Fields
import backtype.storm.{LocalCluster, Config}

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/27/12 11:39 PM
 *
 */
object Main extends App {
  val builder = new TopologyBuilder()

  builder.setSpout("randsentence", new RandomSentenceSpout)
  builder.setBolt("split", new SplitSentence, 8).shuffleGrouping("randsentence")
  builder.setBolt("count", new WordCount, 12).fieldsGrouping("split", new Fields("word"))

  val conf = new Config()
  conf setDebug true

  val cluster = new LocalCluster()
  cluster.submitTopology("test", conf, builder.createTopology())
  Thread sleep 10000
  cluster.killTopology("test")
  cluster.shutdown()
}
