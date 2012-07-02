package org.linkerz.crawler.core.download

import org.linkerz.crawler.core.model.{WebPage, WebLink}
import org.jsoup.Jsoup

/**
 * The Class BaseDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 11:54 PM
 *
 */
case class BaseDownloadResult(webPage : WebPage) extends DownloadResult

class BaseDownloader extends Downloader {
  /**
   * Download for a link.
   * @param webLink
   * @return
   */
  def download(webLink: WebLink): DownloadResult = {
    val document = Jsoup.connect(webLink.url).get()
    val webPage = new WebPage
    webPage.webLink = webLink
    webPage.title = document.title
    webPage.html = document.html
    new BaseDownloadResult(webPage)
  }
}
