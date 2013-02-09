package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.factory.DownloadFactory
import org.linkerz.crawl.topology.downloader.Downloader

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("sessionId", "event")) {

  @transient
  private var imageDownloader: Downloader = _

  setup {
    imageDownloader = DownloadFactory.createImageDownloader()
  }

}
