package org.linkerz.recommendation.spout

import grizzled.slf4j.Logging
import backtype.storm.utils.Utils
import storm.scala.dsl.StormSpout
import org.linkerz.core.actor.Akka
import akka.actor.Props
import scala.concurrent.duration._
import org.linkerz.recommendation.actor.ScheduleActor

/**
 * The Class RecommendationSpout.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 2:27 AM
 *
 */
class RecommendationSpout extends StormSpout(outputFields = List("userId", "event")) with Logging {

  setup {
    val scheduleActor = Akka.system.actorOf(Props(new ScheduleActor(_collector)))
    //Schedule the actor run for every 15 minutes.
    import Akka.system.dispatcher
    Akka.system.scheduler.schedule(10 seconds, 5 minutes, scheduleActor, "run")
  }

  def nextTuple() {
    //Sleep 10ms for not wasting cpu
    Utils sleep 10
  }
}
