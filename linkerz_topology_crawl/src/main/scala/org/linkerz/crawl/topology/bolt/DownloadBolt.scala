package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.event.FetchDone
import org.linkerz.crawl.topology.downloader.NewsDownloader
import org.linkerz.crawl.topology.factory.DownloadFactory

/**
 * The Class DownloadBolt.
 *
 * @author Nguyen Duc Dung
 * @since 5/4/13 4:19 AM
 *
 */
class DownloadBolt extends StormBolt(outputFields = List("feedId", "event")) with Logging {

  @transient
  private var downloader: NewsDownloader = _

  setup {
    downloader = DownloadFactory.createNewsDownloader()
  }

  execute {
    implicit tuple => tuple matchSeq {
      case FetchDone(entry) => {
        downloader.download(entry)
      }
    }
  }

}
