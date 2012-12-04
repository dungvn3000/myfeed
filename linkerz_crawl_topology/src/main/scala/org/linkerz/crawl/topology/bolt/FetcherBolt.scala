package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{Parse, Fetch}
import org.linkerz.crawl.topology.factory.DownloadFactory
import org.linkerz.crawl.topology.downloader.Downloader
import backtype.storm.utils.Utils
import grizzled.slf4j.Logging
import java.util.UUID

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("sessionId", "event")) with Logging {

  private var downloader: Downloader = _

  setup {
    downloader = DownloadFactory.createDownloader()
  }

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, Fetch(job)) => {
        try {
          //Delay time for each job.
          if (job.politenessDelay > 0 && job.parent.isDefined) Utils sleep job.politenessDelay
          downloader download job
        } catch {
          case ex: Exception => {
            job.error(ex.getMessage, getClass.getName, job.webUrl, ex)
            _collector reportError ex
          }
        }
        tuple emit(sessionId, Parse(job))
      }
    }
    tuple.ack()
  }
}
