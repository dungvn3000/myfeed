package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Parse, Fetch}
import org.linkerz.crawl.topology.factory.DownloadFactory
import org.linkerz.crawl.topology.downloader.Downloader
import backtype.storm.utils.Utils
import grizzled.slf4j.Logging

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("fetch")) with Logging {

  private var downloader: Downloader = _

  setup {
    downloader = DownloadFactory.createDownloader()
  }

  override def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(Fetch(session, job)) => {
        try {
          //Delay time for each job.
          if (job.politenessDelay > 0 && job.parent.isDefined) Utils sleep job.politenessDelay
          downloader download job
        } catch {
          case ex: Exception => {
            error("Fetch error: " + job.webUrl.url)
            _collector reportError ex
          }
        }
        if (job.result.exists(!_.isError)) {
          tuple emit Parse(session, job)
        }
        tuple.ack()
      }
    }
  }
}
