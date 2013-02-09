package org.linkerz.crawl.topology.spout

import akka.util.duration._
import grizzled.slf4j.Logging
import storm.scala.dsl.StormSpout
import backtype.storm.utils.Utils
import org.linkerz.logger.DBLogger
import org.linkerz.crawl.topology.actor.ScheduleActor
import akka.actor.Props
import org.linkerz.core.actor.Akka

/**
 * This spout will loop the feed list for every 15 minutes.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:42 AM
 *
 */
class FeedSpout extends StormSpout(outputFields = List("feed")) with Logging with DBLogger {

  setup {
    val scheduleActor = Akka.system.actorOf(Props(new ScheduleActor(_collector)))
    //Schedule the actor run for every 15 minutes.
    Akka.system.scheduler.schedule(10 seconds, 10 minutes, scheduleActor, "run")
  }

  def nextTuple() {
    //Sleep 10ms for not wasting cpu
    Utils sleep 10
  }
}
