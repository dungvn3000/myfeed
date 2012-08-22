/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.controller

import org.linkerz.crawler.core.controller.CrawlerController
import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.quartz._
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.mongodb.model.NewFeed
import org.quartz.JobBuilder._
import org.quartz.TriggerBuilder._
import org.quartz.SimpleScheduleBuilder._
import collection.JavaConversions._
import NewFeedController._

/**
 * The Class NewFeedController.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 3:56 AM
 *
 */

class NewFeedController {

  @BeanProperty
  var crawlerController: CrawlerController = _

  @BeanProperty
  var mongoOperations: MongoOperations = _

  @BeanProperty
  var scheduler: Scheduler = _

  /**
   * Politeness delay in milliseconds (delay between sending two Feeder).
   * default 5 minutes.
   */
  @BeanProperty
  var politenessDelay = 1000 * 60 * 5

  def start() {
    val newFeeds = mongoOperations.findAll(classOf[NewFeed])
    newFeeds.foreach(newFeed => {
      val jobDetail = newJob(classOf[NewFeedJob]).build()
      jobDetail.getJobDataMap.put(CONTROLLER, crawlerController)
      jobDetail.getJobDataMap.put(NEW_FEED, newFeed)

      val trigger = newTrigger().startNow()
        .withSchedule(repeatMinutelyForever(newFeed.time)).build()
      scheduler.scheduleJob(jobDetail, trigger)

      //Sleep 5 minute before start another feed.
      Thread.sleep(politenessDelay)
    })
  }

  def stop() {
    crawlerController.stop()
  }

}

object NewFeedController {
  val CONTROLLER = "controller"
  val NEW_FEED = "newFeed"
}

/**
 * This job using quartz job to warp the real job, for scheduling the job.
 */
class NewFeedJob extends Job {
  def execute(context: JobExecutionContext) {
    val controller = context.getJobDetail.getJobDataMap.get(CONTROLLER)
    val newFeed = context.getJobDetail.getJobDataMap.get(NEW_FEED).asInstanceOf[NewFeed]
    if (controller.isInstanceOf[CrawlerController]) {
      val job = new CrawlJob(newFeed.url)
      if (newFeed.excludeUrl != null) {
        job.excludeUrl = newFeed.excludeUrl.toList
      }
      if (newFeed.urlRegex != null) {
        job.urlRegex = newFeed.urlRegex.toList
      }
      controller.asInstanceOf[CrawlerController] ! job
    }
  }
}
