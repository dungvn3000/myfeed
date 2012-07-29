/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser

import org.linkerz.crawler.core.downloader.DownloadResult
import grizzled.slf4j.Logging
import org.apache.tika.parser.ParseContext
import org.apache.tika.parser.html.HtmlParser
import collection.mutable.ListBuffer
import org.linkerz.crawler.core.model.{WebPage, WebUrl}
import org.apache.tika.metadata.Metadata
import edu.uci.ics.crawler4j.parser.HtmlContentHandler
import java.io.{File, ByteArrayInputStream}

import collection.JavaConversions._
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import com.cybozu.labs.langdetect.DetectorFactory
import com.google.common.io.Resources

/**
 * The Class Parser.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:09 AM
 *
 */

class Parser extends Logging {

  val htmlParser = new HtmlParser
  val parseContext = new ParseContext
  val htmlHandler = new HtmlContentHandler

  def parse(downloadResult: DownloadResult): ParserResult = {
    info("Parse: " + downloadResult.webUrl)
    var webUrls = new ListBuffer[WebUrl]

    if (downloadResult.byteContent != null) {
      val metadata = new Metadata
      val inputStream = new ByteArrayInputStream(downloadResult.byteContent)
      htmlParser.parse(inputStream, htmlHandler, metadata, parseContext)
      val title = metadata.get(Metadata.TITLE)
      val content = htmlHandler.getBodyText
      DetectorFactory.loadProfile(new File(Resources.getResource("profiles").toURI))
      val detector = DetectorFactory.create
      detector.append(content)
      val language = detector.detect

      val webPage = new WebPage(downloadResult.webUrl, title, content)
      webPage.language = language

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
    new ParserResult(webUrls.toList)
  }

}
