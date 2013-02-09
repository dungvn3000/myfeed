package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.factory.DownloaderFactory
import org.linkerz.crawl.topology.model.WebPage

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("feedId", "webPage")) {

  @transient
  private val imageDownloader = DownloaderFactory.createImageDownloader()

  execute(implicit tuple => tuple matchSeq {
    case Seq(feedId, webPage: WebPage) => {
      imageDownloader.download(webPage)
      tuple.emit(webPage)
    }
  })

}
