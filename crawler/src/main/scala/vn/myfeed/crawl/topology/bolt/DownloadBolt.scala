package vn.myfeed.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import vn.myfeed.crawl.topology.event.{DownloadDone, FetchDone}
import vn.myfeed.crawl.topology.downloader.Downloader
import vn.myfeed.crawl.topology.factory.DownloadFactory
import org.apache.commons.lang.StringUtils
import org.apache.commons.validator.routines.UrlValidator
import vn.myfeed.dao.NewsDao
import ch.sentric.URL

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
      case Seq(feedId, FetchDone(feed, entry)) => {
        val url = StringUtils.trimToEmpty(entry.getLink)
        if (urlValidator.isValid(url)) {
          try {
            val normalizationUrl = new URL(url)
            if (NewsDao.findOneById(normalizationUrl.getNormalizedUrl).isEmpty) {
              downloader.download(url).map(result => {
                tuple.emit(feedId, DownloadDone(feed, entry, result))
              })
            }
          } catch {
            case ex: Exception => _collector.reportError(ex)
          }
        }
        tuple.ack()
      }
    }
  }


}
