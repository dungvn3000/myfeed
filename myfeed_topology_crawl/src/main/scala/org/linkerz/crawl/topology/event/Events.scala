package org.linkerz.crawl.topology.event

import org.linkerz.model.{News, Feed}
import org.linkerz.crawl.topology.downloader.DownloadResult
import org.horrabin.horrorss.RssItemBean

/**
 * The Class Event.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 3:49 AM
 *
 */
sealed trait Event extends Serializable

case class Start(feed: Feed) extends Event

case class FetchDone(feed: Feed, item: RssItemBean) extends Event

case class DownloadDone(feed: Feed, item: RssItemBean, result: DownloadResult) extends Event

case class ParseDone(feed: Feed, news: News) extends Event

case class PersistentDone(news: News) extends Event
