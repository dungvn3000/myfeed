package org.linkerz.crawl.topology.event

import org.linkerz.crawl.topology.job.CrawlJob

/**
 * The Class Event.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:49 AM
 *
 */
sealed trait Event

//This event starting from the spout, using for start crawling an url.
case class Start(job: CrawlJob) extends Event

//This event will be sent from the spout. When a job was acked.
object Ack extends Event

//This event will be sent from the spout. When a job was failed.
object Fail extends Event

//This event come form persistent bolt, after it sync result with the database,
//the event will be sent to the crawler bolt to decide whether go for it or not.
case class Handle(job: CrawlJob) extends Event

case class Fetch(job: CrawlJob) extends Event

case class Parse(job: CrawlJob) extends Event

case class MetaFetch(job: CrawlJob) extends Event

case class Persistent(job: CrawlJob) extends Event
