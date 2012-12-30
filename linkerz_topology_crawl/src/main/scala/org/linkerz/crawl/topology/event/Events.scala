package org.linkerz.crawl.topology.event

import org.linkerz.crawl.topology.job.CrawlJob

/**
 * The Class Event.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:49 AM
 *
 */
sealed trait Event extends Serializable

//This event starting from the spout, using for start crawling an url.
case class Start(job: CrawlJob) extends Event

//This event will be sent from the spout. When a job was acked.
object Ack extends Event

//This event will be sent from the spout. When a job was failed.
object Fail extends Event

//This event will sent by handler bolt, after the bolt finished it's job.
case class Handle(job: CrawlJob) extends Event

//This event will sent by fetcher bolt, after the bolt finished it's job.
case class Fetch(job: CrawlJob) extends Event

//This event will sent by parser bolt, after the bolt finished it's job.
case class Parse(job: CrawlJob) extends Event

//This event will sent by meta fetcher bolt, after the bolt finished it's job.
case class MetaFetch(job: CrawlJob) extends Event

//This event will sent by persistent bolt, after the bolt finished it's job.
case class Persistent(job: CrawlJob) extends Event
