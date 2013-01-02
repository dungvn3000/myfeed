package org.linkerz.topology.delivery.actor

import backtype.storm.spout.SpoutOutputCollector
import akka.actor.Actor
import org.linkerz.logger.DBLogger
import grizzled.slf4j.Logging
import org.linkerz.dao.UserDao
import com.mongodb.casbah.commons.MongoDBObject
import backtype.storm.tuple.Values
import org.linkerz.topology.delivery.event.Start

/**
 * The Class ScheduleActor.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 12:54 AM
 *
 */
class ScheduleActor(collector: SpoutOutputCollector) extends Actor with DBLogger with Logging {
  protected def receive = {
    case "run" => {
      val users = UserDao.find(MongoDBObject.empty).toList
      users.foreach(user => {
        collector.emit(new Values(user._id, Start))
      })
    }
  }
}
