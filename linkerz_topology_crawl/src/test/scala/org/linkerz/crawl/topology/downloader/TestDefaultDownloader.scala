package org.linkerz.crawl.topology.downloader

import org.linkerz.crawl.topology.factory.DownloadFactory
import org.junit.Test

/**
 * The Class TestDefaultDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 5:56 PM
 *
 */
class TestDefaultDownloader {

  @Test
  def testDownloader() {
    val downloader = DownloadFactory.createDownloader()
//    val imageDownloader = DownloadFactory.createImageDownloader()
//    val feeds = FeedDao.find(MongoDBObject.empty).toList
//    val parser = new LinkerZParser(feeds)
//    val crawlJob = new CrawlJob("http://news.zing.vn/xa-hoi/tap-doan-moc-tui-xuong-pho-sai-gon/a293547.html")
//    downloader.download(crawlJob)
//    parser.parse(crawlJob)
//    imageDownloader.download(crawlJob)
//
//    assert(crawlJob.result.get.responseCode == 200)
//    if (crawlJob.result.get.isArticle) {
//      println(crawlJob.result.get.title)
//      println(crawlJob.result.get.description.get)
//      println(crawlJob.result.get.potentialImages)
//    }
  }

}
