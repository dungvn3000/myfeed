package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.event.{DownloadDone, FetchDone}
import org.linkerz.crawl.topology.downloader.Downloader
import org.linkerz.crawl.topology.factory.DownloadFactory
import org.apache.commons.lang.StringUtils
import org.apache.commons.validator.routines.UrlValidator
import org.linkerz.dao.NewsDao

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

  @transient
  private var urlValidator: UrlValidator = _

  setup {
    downloader = DownloadFactory.createDownloader()
    urlValidator = new UrlValidator
  }

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(feedId, FetchDone(feed, item)) => {
        val url = StringUtils.trimToEmpty(item.getLink)
        if (urlValidator.isValid(url)) {
          if(NewsDao.findByUrl(url).isEmpty) {
            try {
              downloader.download(url).map(result => {
                tuple.emit(feedId, DownloadDone(feed, item, result))
              })
            } catch {
              case ex: Exception => _collector.reportError(ex)
            }
          }
        }
        tuple.ack()
      }
    }
  }

}
