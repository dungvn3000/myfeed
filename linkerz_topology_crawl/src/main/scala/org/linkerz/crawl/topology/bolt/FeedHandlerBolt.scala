package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.factory.{DownloaderFactory, ParserFactory}
import org.linkerz.model.Feed
import org.linkerz.crawl.topology.model.WebUrl

/**
 * The mission of this bolt will receive job from the feed spot and emit it to a fetcher. On the other hand this bolt
 * will receive result from parser and decide whether continue crawling or not.
 *
 *
 * @author Nguyen Duc Dung
 * @since 11/29/12 11:41 PM
 *
 */
class FeedHandlerBolt extends StormBolt(outputFields = List("entry")) with Logging {

  @transient
  private val downloader = DownloaderFactory.createDefaultDownloader()

  @transient
  private val parser = ParserFactory.createRssParser()

  execute(implicit tuple => tuple matchSeq {
    case Seq(feed: Feed) => {
      feed.urls.foreach(url => {
        val response = downloader.download(new WebUrl(url))
        val entries = parser.parse(response)
        entries.foreach(entry => {
          tuple.emit(entry)
        })
      })
    }
  })

}
