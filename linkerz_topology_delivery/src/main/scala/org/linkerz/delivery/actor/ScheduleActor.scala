package org.linkerz.delivery.actor

import backtype.storm.spout.SpoutOutputCollector
import akka.actor.Actor
import org.linkerz.logger.DBLogger
import grizzled.slf4j.Logging
import org.linkerz.dao.UserDao
import org.linkerz.delivery.event.Start
import backtype.storm.tuple.Values

/**
 * The Class ScheduleActor.
 *
 * @author Nguyen Duc Dung
 * @since 12/17/12 1:15 AM
 *
 */
class ScheduleActor(collector: SpoutOutputCollector) extends Actor with DBLogger with Logging {
  def receive = {
    case "run" => {
      val users = UserDao.all
      users.foreach(user => {
        println("Delivery to user " + user.id)
        collector.emit(new Values(user._id, Start))
      })
    }
  }
}
