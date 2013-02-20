package org.linkerz.recommendation.actor

import backtype.storm.spout.SpoutOutputCollector
import akka.actor.Actor
import org.linkerz.logger.DBLogger
import grizzled.slf4j.Logging
import org.linkerz.dao.UserDao
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.recommendation.event.Recommendation
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
      val users = UserDao.find(MongoDBObject.empty).toList
      users.foreach(user => {
        println("Recommend for user " + user.id)
        collector.emit(new Values(user._id, Recommendation))
      })
    }
  }
}
