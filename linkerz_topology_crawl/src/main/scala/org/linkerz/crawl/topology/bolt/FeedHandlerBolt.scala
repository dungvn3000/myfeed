package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.factory.{DownloaderFactory, ParserFactory}
import org.linkerz.model.Feed
import org.linkerz.crawl.topology.model.WebUrl
import org.linkerz.crawl.topology.downloader.DefaultDownloader
import org.linkerz.crawl.topology.parser.RssParser

/**
 * The mission of this bolt will receive job from the feed spot and emit it to a fetcher. On the other hand this bolt
 * will receive result from parser and decide whether continue crawling or not.
 *
 *
 * @author Nguyen Duc Dung
 * @since 11/29/12 11:41 PM
 *
 */
class FeedHandlerBolt extends StormBolt(outputFields = List("feedId", "entry")) with Logging {

  @transient
  private var downloader: DefaultDownloader = _

  @transient
  private var parser: RssParser = _

  setup {
    downloader = DownloaderFactory.createDefaultDownloader()
    parser = ParserFactory.createRssParser()
  }

  execute(implicit tuple => tuple matchSeq {
    case Seq(feedId, feed: Feed) => {
      feed.urls.foreach(url => {
        val response = downloader.download(new WebUrl(url))
        val entries = parser.parse(response)
        entries.foreach(entry => {
          tuple.emit(feedId, entry)
        })

        if (entries.isEmpty) {
          info("Can't parse this url: " + url)
        }
      })
    }
  })

}
