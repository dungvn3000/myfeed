package org.linkerz.crawl.topology.event

import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.session.CrawlSession

/**
 * The Class Event.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:49 AM
 *
 */
sealed trait Event

//This event starting from the spout, using for start crawling an url.
case class StartWith(session: CrawlSession, job: CrawlJob) extends Event

//This event come form persistent bolt, after it sync result with the database,
//the event will be sent to the crawler bolt to decide whether go for it or not.
case class Handle(session: CrawlSession, job: CrawlJob) extends Event

case class Fetch(session: CrawlSession, job: CrawlJob) extends Event

case class Parse(session: CrawlSession, job: CrawlJob) extends Event

case class MetaFetch(session: CrawlSession, job: CrawlJob) extends Event

case class Persistent(session: CrawlSession, job: CrawlJob) extends Event