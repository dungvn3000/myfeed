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

  def start() {
    val newFeeds = mongoOperations.findAll(classOf[NewFeed])
    newFeeds.foreach(newFeed => {
      val jobDetail = newJob(classOf[NewFeedJob]).build()
      jobDetail.getJobDataMap.put(NewFeedController.CONTROLLER, crawlerController)
      jobDetail.getJobDataMap.put(NewFeedController.URL, newFeed.url)

      val trigger = newTrigger().startNow()
        .withSchedule(repeatMinutelyForever(newFeed.time)).build()
      scheduler.scheduleJob(jobDetail, trigger)
    })
  }

  def stop() {
    crawlerController.stop()
  }

}

object NewFeedController {
  val CONTROLLER = "controller"
  val URL = "url"
}

class NewFeedJob extends Job {
  def execute(context: JobExecutionContext) {
    val controller = context.getJobDetail.getJobDataMap.get(NewFeedController.CONTROLLER)
    val url = context.getJobDetail.getJobDataMap.getString(NewFeedController.URL)
    if (controller.isInstanceOf[CrawlerController]) {
      controller.asInstanceOf[CrawlerController].add(new CrawlJob(url))
    }
  }
}
