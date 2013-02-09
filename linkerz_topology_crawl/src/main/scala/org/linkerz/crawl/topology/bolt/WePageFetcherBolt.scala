package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.factory.DownloaderFactory
import com.sun.syndication.feed.synd.SyndEntry
import org.linkerz.crawl.topology.model.WebUrl

/**
 * This bolt is simply download a url and emit it to a parser.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 12:55 AM
 *
 */
class WePageFetcherBolt extends StormBolt(outputFields = List("webPage")) with Logging {

  @transient
  private val downloader = DownloaderFactory.createWebPageDownloader()

  execute(implicit tuple => tuple matchSeq {
    case Seq(entry: SyndEntry) => {
      downloader.download(new WebUrl(entry.getLink)).map(tuple.emit(_))
    }
  })


}
