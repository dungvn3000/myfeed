package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{Persistent, MetaFetch}
import org.linkerz.crawl.topology.downloader.Downloader
import java.util.UUID

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("sessionId", "event")) {

  var downloader: Downloader = _

  setup {
    //    downloader = DownloadFactory.createImageDownloader()
  }

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, MetaFetch(job)) => {
        //        downloader download job
        tuple emit(sessionId, Persistent(job))
      }
    }
    tuple.ack()
  }
}
