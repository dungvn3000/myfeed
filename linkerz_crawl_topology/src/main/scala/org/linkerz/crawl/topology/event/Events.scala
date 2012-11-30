package org.linkerz.crawl.topology.event

import org.linkerz.crawler.bot.job.FeedJob

/**
 * The Class Event.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:49 AM
 *
 */
sealed trait Event

//This event starting from the spout, using for start crawling an url.
case class StartWith(feedJob: FeedJob) extends Event

//This event come form persistent bolt, after it sync result with the database,
//the event will be sent to the crawler bolt to decide whether go for it or not.
case class Crawl(feedJob: FeedJob) extends Event

case class Fetch(feedJob: FeedJob) extends Event

case class Parse(feedJob: FeedJob) extends Event

case class MetaFetch(feedJob: FeedJob) extends Event

case class Persistent(feedJob: FeedJob) extends Event