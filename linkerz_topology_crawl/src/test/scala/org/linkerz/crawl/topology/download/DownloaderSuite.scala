package org.linkerz.crawl.topology.download

import org.scalatest.FunSuite
import org.linkerz.crawl.topology.factory.DownloaderFactory
import org.linkerz.crawl.topology.model.WebUrl

/**
 * The Class DownloadSuite.
 *
 * @author Nguyen Duc Dung
 * @since 2/10/13 12:30 PM
 *
 */
class DownloaderSuite extends FunSuite {

  test("test downloader") {
    val defaultDownloader = DownloaderFactory.createDefaultDownloader()
    val webPageDownloader = DownloaderFactory.createWebPageDownloader()
    val imageDownloader = DownloaderFactory.createImageDownloader()

    val url = new WebUrl("http://vnexpress.net/gl/phap-luat/2013/02/nam-canh-sat-hinh-su-co-duyen-pha-an-dip-tet/")

    val response = defaultDownloader.download(url)
    assert(response.getStatusLine.getStatusCode == 200)

    val webPage = webPageDownloader.download(url)
    assert(webPage.isDefined)

    webPage.get.potentialImages= List(
      "http://vnexpress.net/images/icons/facebook.gif",
      "http://vnexpress.net/Files/Subject/3b/be/16/4d/tham.jpg",
      "http://vnexpress.net/Files/Subject/3b/be/16/4d/bang.jpg"
    )

    imageDownloader.download(webPage.get)
    assert(webPage.get.featureImage.isDefined)
  }

}
