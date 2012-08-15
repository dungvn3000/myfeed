/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser

import org.apache.tika.parser.html.HtmlParser
import org.apache.tika.parser.ParseContext
import edu.uci.ics.crawler4j.parser.HtmlContentHandler
import org.linkerz.crawler.core.downloader.DownloadResult
import collection.mutable.ListBuffer
import org.linkerz.crawler.core.model.{WebPage, WebUrl}
import org.apache.tika.metadata.Metadata
import java.io.ByteArrayInputStream
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import grizzled.slf4j.Logging
import collection.JavaConversions._
/**
 * The Class DefaultParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:04 PM
 *
 */

class DefaultParser extends Parser with Logging {

  val htmlParser = new HtmlParser
  val parseContext = new ParseContext
  val htmlHandler = new HtmlContentHandler

  def parse(downloadResult: DownloadResult): ParserResult = {
    info("Parse: " + downloadResult.webUrl.url)
    var webUrls = new ListBuffer[WebUrl]
    val webPage = new WebPage

    if (downloadResult.byteContent != null) {
      val metadata = new Metadata
      val inputStream = new ByteArrayInputStream(downloadResult.byteContent)
      htmlParser.parse(inputStream, htmlHandler, metadata, parseContext)

      webPage.contentEncoding = metadata.get("Content-Encoding")
      webPage.webUrl = downloadResult.webUrl
      webPage.content = downloadResult.byteContent

      //Get web page content
      //      val title = metadata.get(Metadata.TITLE)
      //      val subTitle = metadata.get(Metadata.DESCRIPTION)
      //      val html = htmlHandler.getBodyText

      //      if (DetectorFactory.getLangList.isEmpty) {
      //        DetectorFactory.loadProfile(new File(Resources.getResource("profiles").toURI))
      //      }
      //
      //      val detector = DetectorFactory.create
      //      detector.setMaxTextLength(1000)
      //      detector.append(html)
      //
      //      try {
      //        val language = detector.detect
      //        webPage.language = language
      //      } catch {
      //        case ex: Exception => error(ex.getMessage, ex)
      //      }

      //Extract links in side a website
      val baseURL = htmlHandler.getBaseUrl
      var contextURL = downloadResult.webUrl.url
      if (baseURL != null) {
        contextURL = baseURL
      }

      htmlHandler.getOutgoingUrls.foreach(urlAnchorPair => {
        var href = urlAnchorPair.getHref
        href = href.trim()
        if (href.length() != 0) {
          var hrefWithoutProtocol = href.toLowerCase
          if (href.startsWith("http://")) {
            hrefWithoutProtocol = href.substring(7)
          }
          if (!hrefWithoutProtocol.contains("javascript:") && !hrefWithoutProtocol.contains("@")) {
            val url = URLCanonicalizer.getCanonicalURL(href, contextURL)
            if (url != null) {
              webUrls += new WebUrl(url)
            }
          }
        }
      })
    }

    webPage.webUrls = webUrls.toList

    new ParserResult(webPage)
  }

}
