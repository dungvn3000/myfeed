package org.linkerz

import backtype.storm.topology.TopologyBuilder
import backtype.storm.testing.TestWordSpout
import backtype.storm.{LocalCluster, Config}
import storm.bolt.{WordCount, SplitSentence}
import storm.spout.RandomSentenceSpout
import backtype.storm.tuple.Fields

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/2/12 3:13 PM
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
