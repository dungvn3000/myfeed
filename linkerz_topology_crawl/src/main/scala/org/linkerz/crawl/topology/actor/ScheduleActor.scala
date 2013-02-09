package org.linkerz.crawl.topology.actor

import org.linkerz.logger.DBLogger
import akka.actor.Actor
import org.linkerz.dao.FeedDao
import com.mongodb.casbah.commons.MongoDBObject
import java.util.UUID
import grizzled.slf4j.Logging
import backtype.storm.spout.SpoutOutputCollector

/**
 * The Class ScheduleActor.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 10:19 PM
 *
 */
class ScheduleActor(collector: SpoutOutputCollector) extends Actor with DBLogger with Logging {

  protected def receive = {
    case "run" => {
      val newFeeds = FeedDao.find(MongoDBObject("enable" -> true)).toList
      newFeeds.foreach(feed => {
        //Make sure the id is unique all the time.
        val sessionId = UUID.randomUUID()
//        collector.emit(new Values(sessionId, Start(job)), sessionId)
      })
    }
  }
}
