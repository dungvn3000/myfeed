package org.linkerz.crawl.topology.event

import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class Event.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:49 AM
 *
 */
sealed trait Event

case class StartWith(feedJob: CrawlJob) extends Event
