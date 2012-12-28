package org.linkerz.crawl.topology.downloader

import org.junit.Test
import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.parser.LinkerZParser
import org.linkerz.dao.NewFeedDao
import com.mongodb.casbah.commons.MongoDBObject

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
    val downloader = new DefaultDownloader()
    val feeds = NewFeedDao.find(MongoDBObject.empty).toList
    val parser = new LinkerZParser(feeds)
    val crawlJob = new CrawlJob("http://news.zing.vn/xa-hoi/nguoi-thanh-pho-bat-dau-do-ve-que-an-tet-duong/a293843.html#home_noibat1")
    downloader.download(crawlJob)
    parser.parse(crawlJob)

    assert(crawlJob.result.get.responseCode == 200)
    if (crawlJob.result.get.isArticle) {
      println(crawlJob.result.get.title)
      println(crawlJob.result.get.description.get)
      println(crawlJob.result.get.potentialImages)
    }
  }

}
