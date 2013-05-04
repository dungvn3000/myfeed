package org.linkerz.crawl.topology.event

import org.linkerz.model.Feed
import com.sun.syndication.feed.synd.SyndEntry

/**
 * The Class Event.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:49 AM
 *
 */
sealed trait Event extends Serializable

case class Start(feed: Feed) extends Event

case class FetchDone(entry: SyndEntry) extends Event

case class DownloadDone(job: FetchJob) extends Event

case class ParseDone(job: FetchJob) extends Event

case class PersistentDone(job: FetchJob) extends Event
