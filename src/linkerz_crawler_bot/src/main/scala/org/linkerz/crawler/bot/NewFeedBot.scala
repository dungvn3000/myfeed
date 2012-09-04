/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot

import reflect.BeanProperty
import org.springframework.data.mongodb.core.MongoOperations
import org.quartz._
import org.linkerz.mongodb.model.NewFeed
import org.quartz.JobBuilder._
import org.quartz.TriggerBuilder._
import org.quartz.SimpleScheduleBuilder._
import collection.JavaConversions._
import NewFeedBot._
import com.rabbitmq.client.{MessageProperties, Channel, Connection, ConnectionFactory}
import util.Marshal
import org.linkerz.crawler.bot.job.NewFeedJob
import actors.Actor

/**
 * The Class NewFeedBot.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 3:56 AM
 *
 */

class NewFeedBot {

  private var _connection: Connection = _
  private var _channel: Channel = _

  @BeanProperty
  var connectionFactory: ConnectionFactory = _

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

  private val botActor = new Actor {
    def act() {
      val newFeeds = mongoOperations.findAll(classOf[NewFeed])
      newFeeds.foreach(newFeed => {
        val jobDetail = newJob(classOf[NewFeedQuartZJob]).build()
        jobDetail.getJobDataMap.put(CHANNEL, _channel)
        jobDetail.getJobDataMap.put(NEW_FEED, newFeed)

        val trigger = newTrigger().startNow()
          .withSchedule(repeatMinutelyForever(newFeed.time)).build()
        NewFeedBot.this.scheduler.scheduleJob(jobDetail, trigger)
        //Sleep 5 minute before start another feed.
//        Thread.sleep(politenessDelay)
      })
    }
  }

  def start() {
    _connection = connectionFactory.newConnection()
    _channel = _connection.createChannel()
    _channel.queueDeclare("jobQueue", true, false, false, null)
    botActor.start()
  }

  def stop() {
    scheduler.shutdown()
    _channel.close()
    _connection.close()
  }

}

object NewFeedBot {
  val CHANNEL = "channel"
  val NEW_FEED = "newFeed"
}

/**
 * This job using quartz job to warp the real job, for scheduling the job.
 */
class NewFeedQuartZJob extends Job {
  def execute(context: JobExecutionContext) {
    val channel = context.getJobDetail.getJobDataMap.get(CHANNEL).asInstanceOf[Channel]
    val newFeed = context.getJobDetail.getJobDataMap.get(NEW_FEED).asInstanceOf[NewFeed]
    if (channel != null) {
      val job = new NewFeedJob(newFeed.url)
      if (newFeed.excludeUrl != null) {
        job.excludeUrl = newFeed.excludeUrl.toList
      }
      if (newFeed.urlRegex != null) {
        job.urlRegex = newFeed.urlRegex.toList
      }
      job.numberOfWorker = 5
      channel.basicPublish("", "jobQueue", MessageProperties.PERSISTENT_BASIC, Marshal.dump(job))
    }
  }
}
