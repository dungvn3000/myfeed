/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.parser

import org.apache.tika.parser.html.HtmlParser
import org.apache.tika.parser.ParseContext
import org.linkerz.crawl.topology.model.WebUrl
import org.apache.tika.metadata.Metadata
import java.io.ByteArrayInputStream
import collection.JavaConversions._
import org.linkerz.crawl.topology.job.CrawlJob
import org.apache.commons.validator.routines.UrlValidator
import java.util
import edu.uci.ics.crawler4j.parser.HtmlContentHandler
import edu.uci.ics.crawler4j.url.URLCanonicalizer

/**
 * The Class DefaultParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:04 PM
 *
 */

class DefaultParser extends Parser {


  def parse(crawlJob: CrawlJob) {

    val htmlParser = new HtmlParser
    val parseContext = new ParseContext
    val htmlHandler = new HtmlContentHandler

    val webUrl = crawlJob.result.get.webUrl
    val webPage = crawlJob.result.get

    info("Parse: " + webUrl.url)

    //Using java list for better performance.
    var webUrls = new util.ArrayList[WebUrl]

    if (webPage.content != null) {
      val metadata = new Metadata
      val inputStream = new ByteArrayInputStream(webPage.content)
      htmlParser.parse(inputStream, htmlHandler, metadata, parseContext)

      webPage.title = metadata.get(Metadata.TITLE)
      if (webPage.title == null) webPage.title = webUrl.url

      webPage.contentEncoding = metadata.get("Content-Encoding")

      //Extract links in side a website
      val baseURL = htmlHandler.getBaseUrl
      var contextURL = webUrl.url
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
          if (!hrefWithoutProtocol.contains("javascript:")
            && !hrefWithoutProtocol.contains("@")
            && !hrefWithoutProtocol.contains("mailto:")) {
            val url = URLCanonicalizer.getCanonicalURL(href, contextURL)
            val urlValidator = new UrlValidator(Array("http","https"))
            if (url != null && urlValidator.isValid(url)) {
              webUrls += new WebUrl(url)
            }
          }
        }
      })
    }

    webPage.webUrls = webUrls
  }

}
