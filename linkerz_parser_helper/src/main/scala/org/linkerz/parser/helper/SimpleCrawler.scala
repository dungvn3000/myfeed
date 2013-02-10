package org.linkerz.parser.helper

import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.factory.{ParserFactory, DownloaderFactory}
import org.linkerz.crawl.topology.model.{WebPage, WebUrl}
import javax.swing.{JLabel, JTextArea}

/**
 * The Class Crawler.
 *
 * @author Nguyen Duc Dung
 * @since 2/10/13 4:34 PM
 *
 */
class SimpleCrawler extends Logging {

  private val defaultDownloader = DownloaderFactory.createDefaultDownloader()
  private val webPageDownloader = DownloaderFactory.createWebPageDownloader()
  private val rssParser = ParserFactory.createRssParser()
  private val webPageParser = ParserFactory.createWebPageParser()

  def crawl(rssUrl: String, configuration: Configuration, resultTxt: JTextArea, statusLbl: JLabel) {
    var count = 0
    var time = System.currentTimeMillis()
    val urls = getUrlList(rssUrl)
    urls.foreach(url => {
      statusLbl.setText("Downloading: " + url)
      download(url).map(webPage => {
        parser(webPage)
        if (webPage.isArticle) {
          resultTxt.append(webPage.webUrl.toString)
          resultTxt.append("\n")

          if (configuration.isShowTitle) {
            resultTxt.append(webPage.title)
            resultTxt.append("\n")
          }

          if (configuration.isShowDescription && webPage.description.isDefined) {
            resultTxt.append(webPage.description.get)
            resultTxt.append("\n")
          }

          if (configuration.isShowText && webPage.text.isDefined) {
            resultTxt.append(webPage.text.get)
            resultTxt.append("\n")
          }

          if (configuration.isShowImage) {
            webPage.potentialImages.foreach(image => {
              resultTxt.append(image)
              resultTxt.append("\n")
            })
          }

          resultTxt.append("\n=========================================================================\n")
          resultTxt.setCaretPosition(resultTxt.getDocument.getLength)
          count += 1
        }
      })
    })

    time = System.currentTimeMillis() - time
    statusLbl.setText("Downloaded: " + count + " websites in " + (time / 1000) + " s")
  }

  def getUrlList(url: String) = {
    val response = defaultDownloader.download(new WebUrl(url))
    val entries = rssParser.parse(response)
    entries.map(_.getLink)
  }

  def download(url: String) = webPageDownloader.download(new WebUrl(url))

  def parser(webPage: WebPage) {
    webPageParser.parse(webPage)
  }

}
