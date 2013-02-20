package org.linkerz.crawl.topology.actor

import org.linkerz.logger.DBLogger
import akka.actor.Actor
import org.linkerz.dao.{BlackUrlDao, FeedDao}
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.crawl.topology.job.CrawlJob
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
      val blackUrls = BlackUrlDao.all
      val newFeeds = FeedDao.all
      newFeeds.foreach(feed => {
        val job = new CrawlJob(feed, newFeeds, blackUrls)
        job.maxDepth = 1 // Set level is 2 because we will get related link.
        job.politenessDelay = 1000
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
