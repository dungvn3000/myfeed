package vn.myfeed.crawl.topology.event

import vn.myfeed.model.{News, Feed}
import vn.myfeed.crawl.topology.downloader.DownloadResult
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

case class FetchDone(feed: Feed, entry: SyndEntry) extends Event

case class DownloadDone(feed: Feed, entry: SyndEntry, result: DownloadResult) extends Event

case class ParseDone(feed: Feed, news: News) extends Event

case class PersistentDone(news: News) extends Event
