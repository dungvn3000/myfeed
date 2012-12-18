package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{Handle, Fetch}
import org.linkerz.crawl.topology.factory.DownloadFactory
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

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, Handle(job)) => {
        val downloader = DownloadFactory.createDownloader
        try {
          //Delay time for each job.
          if (job.politenessDelay > 0 && job.parent.isDefined) Utils sleep job.politenessDelay
          downloader download job
        } catch {
          case ex: Exception => {
            job.error(ex.getMessage, getClass.getName, job.webUrl, ex)
            _collector reportError ex
          }
        } finally {
          downloader.close()
        }
        tuple emit(sessionId, Fetch(job))
      }
    }
    tuple.ack()
  }
}
