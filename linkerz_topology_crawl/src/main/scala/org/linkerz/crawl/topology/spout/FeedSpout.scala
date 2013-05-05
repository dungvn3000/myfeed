package org.linkerz.crawl.topology.spout

import storm.scala.dsl.StormSpout
import backtype.storm.utils.Utils
import org.linkerz.crawl.topology.actor.ScheduleActor
import akka.actor.Props
import org.linkerz.core.actor.Akka
import scala.concurrent.duration._

/**
 * This spout will loop the feed list for every 15 minutes.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:42 AM
 *
 */
class FeedSpout extends StormSpout(outputFields = List("event")) {

  setup {
    val scheduleActor = Akka.system.actorOf(Props(new ScheduleActor(_collector)))
    //Schedule the actor run for every 15 minutes.
    import Akka.system.dispatcher
    Akka.system.scheduler.schedule(10 seconds, 30 minutes, scheduleActor, "run")
  }

  def nextTuple() {
    //Sleep 10ms for not wasting cpu
    Utils sleep 10
  }
}
