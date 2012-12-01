package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Persistent, MetaFetch}
import org.linkerz.crawl.topology.factory.{DefaultDownloadFactory, DownloadFactory}
import org.linkerz.crawl.topology.downloader.Downloader

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("metaFetch")) {

  var downloadFactory: DownloadFactory = _
  var downloader: Downloader = _

  setup {
    downloadFactory = new DefaultDownloadFactory
    downloader = downloadFactory.createImageDownloader()
  }

  override def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(MetaFetch(session, job)) => {
        downloader download job
        tuple emit Persistent(session, job)
      }
    }
    tuple.ack
  }
}
