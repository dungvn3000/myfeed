package org.linkerz.crawl.topology.downloader

import org.junit.Test
import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.parser.LinkerZParser

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
    val parser = new LinkerZParser
    val crawlJob = new CrawlJob("http://genk.vn/internet/facebook-mac-ao-moi-cho-tinh-nang-tin-nhan-chen-quang-cao-vao-ket-qua-truy-van-cua-nguoi-dung-20120824122528171.chn")
    downloader.download(crawlJob)
    parser.parse(crawlJob)

    assert(crawlJob.result.get.responseCode == 200)
    println(crawlJob.result.get.title)
    println(crawlJob.result.get.description.get)
  }

}
