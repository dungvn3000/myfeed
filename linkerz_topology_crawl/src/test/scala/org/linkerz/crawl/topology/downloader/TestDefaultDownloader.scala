package org.linkerz.crawl.topology.downloader

import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.parser.LinkerZParser
import org.linkerz.dao.FeedDao
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.crawl.topology.factory.DownloadFactory
import org.junit.Test
import crawlercommons.fetcher.file.SimpleFileFetcher

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
    val imageDownloader = DownloadFactory.createImageDownloader()
    val feeds = FeedDao.find(MongoDBObject.empty).toList
    val parser = new LinkerZParser(feeds)
    val crawlJob = new CrawlJob("http://vnexpress.net/gl/xa-hoi/du-lich/2013/02/ruoc-qua-phao-6-met-o-lang-dong-ky/")
    downloader.download(crawlJob)
    parser.parse(crawlJob)
    imageDownloader.download(crawlJob)

    assert(crawlJob.responseCode == 200)
    if (crawlJob.result.get.isArticle) {
      println(crawlJob.result.get.title)
      println(crawlJob.result.get.description.get)
      println(crawlJob.result.get.potentialImages)
      println(crawlJob.result.get.featureImage)
    }
  }

}
