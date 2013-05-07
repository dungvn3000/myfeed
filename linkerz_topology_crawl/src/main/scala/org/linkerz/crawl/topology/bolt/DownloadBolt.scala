package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.event.{DownloadDone, FetchDone}
import org.linkerz.crawl.topology.downloader.DefaultDownloader
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
  private var downloader: DefaultDownloader = _

  setup {
    downloader = DownloadFactory.createDownloader()
  }

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(feedId, FetchDone(feed, entry)) => {
        val url = StringUtils.trimToEmpty(entry.getLink)
        if (StringUtils.isNotEmpty(url)) {
          downloader.download(url).map(result => {
            tuple.emit(feedId, DownloadDone(feed, result))
          })
        }
      }
    }
      tuple.ack()
  }

}
