package org.linkerz

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import backtype.storm.topology.TopologyBuilder
import backtype.storm.testing.TestWordSpout
import backtype.storm.{LocalCluster, Config}

/**
 * The Class Main.
 *
 * @author Nguyen Duc Dung
 * @since 11/2/12 3:13 PM
 *
 */
object Main extends App {

  val builder = new TopologyBuilder()

  builder.setSpout("words", new TestWordSpout(), 10)
  builder.setBolt("exclaim1", new ExclamationBolt, 3)
    .shuffleGrouping("words")
  builder.setBolt("exclaim2", new ExclamationBolt, 2)
    .shuffleGrouping("exclaim1")

  val conf = new Config()
  conf setDebug true

  val cluster = new LocalCluster()
  cluster.submitTopology("test", conf, builder.createTopology())
  Thread sleep 10000
  cluster.killTopology("test")
  cluster.shutdown()
}

class ExclamationBolt extends StormBolt(outputFields = List("word")) {
  def execute(t: Tuple) {
    t emit (t.getString(0) + "!!!")
    t ack
  }
}
