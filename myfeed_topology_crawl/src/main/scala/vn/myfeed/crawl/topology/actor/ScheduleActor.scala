package vn.myfeed.crawl.topology.actor

import vn.myfeed.logger.DBLogger
import akka.actor.Actor
import vn.myfeed.dao.FeedDao
import vn.myfeed.crawl.topology.event.Start
import grizzled.slf4j.Logging
import backtype.storm.tuple.Values
import backtype.storm.spout.SpoutOutputCollector
import backtype.storm.utils.Utils

/**
 * The Class ScheduleActor.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 10:19 PM
 *
 */
class ScheduleActor(collector: SpoutOutputCollector) extends Actor with DBLogger with Logging {
  def receive = {
    case "run" => {
      val newFeeds = FeedDao.all
      newFeeds.foreach(feed => {
        info("Start fetching " + feed.url)
        collector.emit(new Values(Start(feed)), feed._id)
        //Delay 10s for each feed.
        Utils sleep 1000 * 10
      })
    }
  }
}
