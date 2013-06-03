package vn.myfeed.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import vn.myfeed.crawl.topology.event.{FetchDone, Start}
import grizzled.slf4j.Logging
import vn.myfeed.crawl.topology.downloader.Downloader
import vn.myfeed.crawl.topology.factory.{ParserFactory, DownloadFactory}
import vn.myfeed.crawl.topology.parser.RssParser
import collection.JavaConversions._

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("feedId", "event")) with Logging {

  @transient
  private var downloader: Downloader = _

  @transient
  private var parser: RssParser = _

  setup {
    downloader = DownloadFactory.createDownloader()
    parser = ParserFactory.createRssParser()
  }

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(Start(feed)) => {
        try {
          downloader.download(feed.url).map(result => {
            val rssFeed = parser.parse(result)
            rssFeed.getItems.foreach(item => {
              tuple.emit(feed._id, FetchDone(feed, item))
            })
          })
          tuple.ack()
        } catch {
          case ex: Exception => {
            _collector.reportError(ex)
            tuple.fail()
          }
        }
      }
    }
  }
}
