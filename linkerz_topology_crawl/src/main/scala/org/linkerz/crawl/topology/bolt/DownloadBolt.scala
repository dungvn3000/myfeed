package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.event.{DownloadDone, FetchDone}
import org.linkerz.crawl.topology.downloader.Downloader
import org.linkerz.crawl.topology.factory.DownloadFactory
import org.apache.commons.lang.StringUtils

/**
 * The Class DownloadBolt.
 *
 * @author Nguyen Duc Dung
 * @since 5/4/13 4:19 AM
 *
 */
class DownloadBolt extends StormBolt(outputFields = List("feedId", "event")) with Logging {

  @transient
  private var downloader: Downloader = _

  setup {
    downloader = DownloadFactory.createDownloaderWithAsyncHttpClient()
  }

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(feedId, FetchDone(feed, entry)) => {
        val url = StringUtils.trimToEmpty(entry.getLink)
        if (StringUtils.isNotEmpty(url)) {
          try {
            downloader.download(url).map(result => {
              tuple.emit(feedId, DownloadDone(feed, result))
            })
          } catch {
            case ex: Exception => _collector.reportError(ex)
          }
        }
        tuple.ack()
      }
    }
  }

}
