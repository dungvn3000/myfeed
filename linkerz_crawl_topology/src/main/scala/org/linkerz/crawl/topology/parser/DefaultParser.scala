/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.model.WebUrl
import org.linkerz.crawl.topology.job.CrawlJob
import java.util

/**
 * The Class DefaultParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:04 PM
 *
 */

class DefaultParser extends Parser {


  def parse(crawlJob: CrawlJob) {

    val webUrl = crawlJob.result.get.webUrl
    val webPage = crawlJob.result.get

    info("Parse: " + webUrl.url)

    //Using java list for better performance.
    var webUrls = new util.ArrayList[WebUrl]

//    if (webPage.content != null) {
//      val inputStream = new ByteArrayInputStream(webPage.content)
//
//      val source = new Source(inputStream)
//
//      webPage.contentEncoding = source.getEncoding
//      if (StringUtils.isBlank(webPage.contentEncoding)) {
//        //Default encoding
//        webPage.contentEncoding = "UTF-8"
//      }
//
//      val links = source.getAllElements(HTMLElementName.A)
//      links.foreach {
//        link => {
//          var href = link.getAttributeValue("href")
//          if (StringUtils.isNotBlank(href)) {
//            href = StringUtils.strip(href)
//            var hrefWithoutProtocol = href
//            if (href.startsWith("http://")) {
//              hrefWithoutProtocol = href.substring(7)
//            }
//            if (!hrefWithoutProtocol.contains("javascript:")
//              && !hrefWithoutProtocol.contains("@")
//              && !hrefWithoutProtocol.contains("mailto:")) {
//              val url = URLCanonicalizer.getCanonicalURL(href, webUrl.baseUrl)
//              val urlValidator = new UrlValidator(Array("http", "https"))
//              if (url != null && urlValidator.isValid(url)) {
//                webUrls += new WebUrl(url)
//              }
//            }
//          }
//        }
//      }
//    }
    webPage.webUrls = webUrls
  }

}
