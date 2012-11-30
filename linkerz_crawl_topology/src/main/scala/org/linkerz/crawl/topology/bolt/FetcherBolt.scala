package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{Parse, Fetch}
import org.linkerz.crawler.core.factory.{DefaultDownloadFactory, DownloadFactory}
import org.linkerz.crawler.core.downloader.Downloader

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class FetcherBolt extends StormBolt(outputFields = List("fetch")) {

  var downloadFactory: DownloadFactory = _
  var downloader: Downloader = _

  setup {
    downloadFactory = new DefaultDownloadFactory
    downloader = downloadFactory.createDownloader()
  }

  def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(Fetch(job)) => {
        println("job = " + job.webUrl.url)
        downloader download job
        if (job.result.exists(!_.isError)) {
          tuple emit Parse(job)
        }
      }
    }
    tuple.ack
  }
}
