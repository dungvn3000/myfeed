package org.linkerz.crawler.core.parser

import org.linkerz.crawler.core.download.{BaseDownloadResult, DownloadResult}
import org.linkerz.crawler.core.model.WebLink
import org.jsoup.Jsoup

import collection.JavaConversions._
import collection.mutable.ListBuffer

/**
 * The Class BaseParser.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 11:56 PM
 *
 */

case class BaseParserResult(webLink : List[WebLink]) extends ParserResult

class BaseParser extends Parser{
  /**
   * Parse a download result.
   * @param downloadResult
   * @return
   */
  def parse(downloadResult: DownloadResult): BaseParserResult = {
    val webPage = downloadResult.asInstanceOf[BaseDownloadResult].webPage
    val document = Jsoup.parse(webPage.html)
    val elements = document.select("a[href]")
    var webLinks = new ListBuffer[WebLink]
    elements.foreach(element => {
      val url = element.attr("abs:href")
      if (url.nonEmpty) {
        webLinks += new WebLink(url)
      }
    })
    new BaseParserResult(webLinks.toList)
  }
}
