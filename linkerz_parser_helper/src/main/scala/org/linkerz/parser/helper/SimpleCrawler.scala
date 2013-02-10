package org.linkerz.parser.helper

import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.factory.{ParserFactory, DownloaderFactory}
import org.linkerz.crawl.topology.model.{WebPage, WebUrl}
import javax.swing.{JLabel, JTextArea}
import com.sun.syndication.feed.synd.SyndEntry

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
    val urls = getEntryList(rssUrl)
    urls.foreach(entry => {
      statusLbl.setText("Downloading: " + entry.getLink)
      download(entry.getLink).map(webPage => {
        parser(webPage, entry)
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

  def getEntryList(url: String) = {
    val response = defaultDownloader.download(new WebUrl(url))
    val entries = rssParser.parse(response)
    entries
  }

  def download(url: String) = webPageDownloader.download(new WebUrl(url))

  def parser(webPage: WebPage, entry: SyndEntry) {
    webPageParser.parse(webPage, Some(entry))
  }

}
