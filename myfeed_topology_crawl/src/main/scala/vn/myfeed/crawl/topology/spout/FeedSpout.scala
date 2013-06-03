package vn.myfeed.crawl.topology.spout

import storm.scala.dsl.StormSpout
import backtype.storm.utils.Utils
import vn.myfeed.crawl.topology.actor.ScheduleActor
import akka.actor.Props
import vn.myfeed.core.actor.Akka
import scala.concurrent.duration._
import vn.myfeed.dao.FeedDao
import org.bson.types.ObjectId
import grizzled.slf4j.Logging

/**
 * This spout will loop the feed list for every 15 minutes.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:42 AM
 *
 */
class FeedSpout extends StormSpout(outputFields = List("event")) with Logging {

  setup {
    val scheduleActor = Akka.system.actorOf(Props(new ScheduleActor(_collector)))
    //Schedule the actor run for every 15 minutes.
    import Akka.system.dispatcher
    Akka.system.scheduler.schedule(10 seconds, 30 minutes, scheduleActor, "run")
  }


  override def ack(msgId: Any) {
    var msg = s"Ack msgId: $msgId "
    val id = new ObjectId(msgId.toString)
    FeedDao.findOneById(id).map(feed => {
      msg += s" Feed Url: ${feed.url}"
    })
    info(msg)
  }

  override def fail(msgId: Any) {
    var msg = s"Fail msgId: $msgId"
    val id = new ObjectId(msgId.toString)
    FeedDao.findOneById(id).map(feed => {
      msg += s" Feed Url: ${feed.url}"
    })
    info(msg)
  }

  def nextTuple() {
    //Sleep 10ms for not wasting cpu
    Utils sleep 10
  }
}
