package org.linkerz.crawl.topology.actor

import org.linkerz.logger.DBLogger
import akka.actor.Actor
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
//      val newFeeds = FeedDao.find(MongoDBObject("enable" -> true)).toList
//      newFeeds.foreach(feed => {
//        val job = new CrawlJob(feed)
//        job.maxDepth = 1 // Set level is 2 because we will get related link.
//        job.politenessDelay = 1000
//        info("Start Crawling " + feed.url)
//        //Make sure the id is unique all the time.
//        val sessionId = UUID.randomUUID()
//        collector.emit(new Values(sessionId, Start(job)), sessionId)
//      })
    }
  }
}
