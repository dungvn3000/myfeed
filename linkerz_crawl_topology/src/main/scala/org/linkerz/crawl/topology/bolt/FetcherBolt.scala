package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Parse, Fetch}
import org.linkerz.crawl.topology.factory.DownloadFactory
import org.linkerz.crawl.topology.downloader.Downloader

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("fetch")) {

  private var downloader: Downloader = _

  setup {
    downloader = DownloadFactory.createDownloader()
  }

  override def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(Fetch(session, job)) => {
        try {
          downloader download job
        } catch {
          case ex: Exception => _collector reportError ex
        }
        if (job.result.exists(!_.isError)) {
          tuple emit Parse(session, job)
        }
        tuple.ack()
      }
    }
  }
}
