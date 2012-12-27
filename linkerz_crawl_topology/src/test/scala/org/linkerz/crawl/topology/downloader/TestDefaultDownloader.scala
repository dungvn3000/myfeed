package org.linkerz.crawl.topology.downloader

import org.junit.Test
import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.parser.DefaultParser

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
    val parser = new DefaultParser
    val crawlJob = new CrawlJob("http://soha.vn/")
    downloader.download(crawlJob)
    parser.parse(crawlJob)

    assert(crawlJob.result.get.responseCode == 200)
    println(crawlJob.result.get.webUrls.size())
  }

}
