package org.linkerz.crawl.topology.event

import org.linkerz.model.{News, Feed}
import com.sun.syndication.feed.synd.SyndEntry
import crawlercommons.fetcher.FetchedResult

/**
 * The Class Event.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:49 AM
 *
 */
sealed trait Event extends Serializable

case class Start(feed: Feed) extends Event

case class FetchDone(feed: Feed, entry: SyndEntry) extends Event

case class DownloadDone(feed: Feed, result: FetchedResult) extends Event

case class ParseDone(feed: Feed, news: News) extends Event

case class PersistentDone(feed: Feed) extends Event
