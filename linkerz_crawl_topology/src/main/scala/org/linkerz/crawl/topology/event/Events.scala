package org.linkerz.crawl.topology.event

import org.linkerz.crawl.topology.job.CrawlJob
import java.util.UUID

/**
 * The Class Event.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:49 AM
 *
 */
sealed trait Event

//This event starting from the spout, using for start crawling an url.
case class Start(sessionId: UUID, job: CrawlJob) extends Event

//This event will be sent from the spout. When a job was acked.
case class Ack(sessionId: UUID) extends Event

//This event will be sent from the spout. When a job was failed.
case class Fail(sessionId: UUID) extends Event

//This event come form persistent bolt, after it sync result with the database,
//the event will be sent to the crawler bolt to decide whether go for it or not.
case class Handle(sessionId: UUID, job: CrawlJob) extends Event

case class Fetch(sessionId: UUID, job: CrawlJob) extends Event

case class Parse(sessionId: UUID, job: CrawlJob) extends Event

case class MetaFetch(sessionId: UUID, job: CrawlJob) extends Event

case class Persistent(sessionId: UUID, job: CrawlJob) extends Event
