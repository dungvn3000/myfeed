package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.factory.DownloadFactory
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.downloader.Downloader

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("sessionId", "event")) with Logging {

  @transient
  private var downloader: Downloader = _

  setup {
    downloader = DownloadFactory.createDownloader()
  }
}
