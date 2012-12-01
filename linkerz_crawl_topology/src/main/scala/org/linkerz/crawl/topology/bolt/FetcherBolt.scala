package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Parse, Fetch}
import org.linkerz.crawl.topology.factory.{DefaultDownloadFactory, DownloadFactory}
import org.linkerz.crawl.topology.downloader.Downloader

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("fetch")) {

  var downloadFactory: DownloadFactory = _
  var downloader: Downloader = _

  setup {
    downloadFactory = new DefaultDownloadFactory
    downloader = downloadFactory.createDownloader()
  }

  override def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(Fetch(session, job)) => {
        downloader download job
        if (job.result.exists(!_.isError)) {
          tuple emit Parse(session, job)
        }
      }
    }
    tuple.ack
  }
}
