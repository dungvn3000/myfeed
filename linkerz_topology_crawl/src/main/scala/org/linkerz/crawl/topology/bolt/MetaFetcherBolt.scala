package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.factory.DownloaderFactory
import org.linkerz.crawl.topology.model.WebPage
import org.linkerz.crawl.topology.downloader.ImageDownloader

/**
 * This bolt is using for download meta data relate to a url.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:08 AM
 *
 */
class MetaFetcherBolt extends StormBolt(outputFields = List("feedId", "webPage")) {

  @transient
  private var imageDownloader: ImageDownloader = _

  setup {
    imageDownloader = DownloaderFactory.createImageDownloader()
  }

  execute(implicit tuple => tuple matchSeq {
    case Seq(feedId, webPage: WebPage) => {
      if (webPage.isArticle) {
        imageDownloader.download(webPage)
        tuple.emit(feedId, webPage)
      }
    }
  })

}
