package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{Parse, MetaFetch}
import org.linkerz.crawl.topology.downloader.Downloader
import java.util.UUID
import org.linkerz.crawl.topology.factory.DownloadFactory

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("sessionId", "event")) {

  private var downloader: Downloader = _

  setup {
    downloader = DownloadFactory.createImageDownloader()
  }

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, Parse(job)) => {
        try {
          if (job.result.exists(!_.isError)) downloader download job
        } catch {
          case ex: Exception => {
            job.error(ex.getMessage, getClass.getName, job.webUrl, ex)
            _collector reportError ex
          }
        }
        tuple emit(sessionId, MetaFetch(job))
      }
    }
    tuple.ack()
  }
}
