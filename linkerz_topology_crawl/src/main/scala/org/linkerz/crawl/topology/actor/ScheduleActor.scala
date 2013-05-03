package org.linkerz.crawl.topology.actor

import org.linkerz.logger.DBLogger
import akka.actor.Actor
import org.linkerz.dao.FeedDao
import org.linkerz.crawl.topology.job.FetchJob
import java.util.UUID
import org.linkerz.crawl.topology.event.Start
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
        val job = new FetchJob(feed, newFeeds)
        info("Start Crawling " + feed.url)
        //Make sure the id is unique all the time.
        val sessionId = UUID.randomUUID()
        collector.emit(new Values(sessionId, Start(job)), sessionId)

        //Delay 10s for each feed.
        Utils sleep 1000 * 10
      })
    }
  }
}
