package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{FetchDone, Start}
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.downloader.DefaultDownloader
import org.linkerz.crawl.topology.factory.{ParserFactory, DownloadFactory}
import org.linkerz.crawl.topology.parser.RssParser

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("feedId", "event")) with Logging {

  @transient
  private var downloader: DefaultDownloader = _

  @transient
  private var parser: RssParser = _

  setup {
    downloader = DownloadFactory.createDownloader()
    parser = ParserFactory.createRssParser()
  }

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(Start(feed)) => {
        downloader.download(feed.url).map(result => {
          val entries = parser.parse(result)
          entries.foreach(entry => {
            tuple.emit(feed._id, FetchDone(feed, entry))
          })
        })
      }
    }
      tuple.ack()
  }
}
