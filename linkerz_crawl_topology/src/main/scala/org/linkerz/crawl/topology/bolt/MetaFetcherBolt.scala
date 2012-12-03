package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Persistent, MetaFetch}
import org.linkerz.crawl.topology.downloader.Downloader

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("metaFetch")) {

  var downloader: Downloader = _

  setup {
//    downloader = DownloadFactory.createImageDownloader()
  }

  override def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(MetaFetch(sessionId, job)) => {
//        downloader download job
        tuple emit Persistent(sessionId, job)
      }
    }
    tuple.ack()
  }
}
