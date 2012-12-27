/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.model.WebUrl
import org.linkerz.crawl.topology.job.CrawlJob
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import org.apache.commons.lang.StringUtils
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import org.apache.commons.validator.routines.UrlValidator
import collection.JavaConversions._

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
    var webUrls = new java.util.ArrayList[WebUrl]

    if (webPage.content != null) {
      val inputStream = new ByteArrayInputStream(webPage.content)
      val doc = Jsoup.parse(inputStream, webPage.contentEncoding, webPage.webUrl.url)

      val links = doc.select("a")
      links.foreach {
        link => {
          var href = link.attr("href")
          if (StringUtils.isNotBlank(href)) {
            href = StringUtils.strip(href)
            var hrefWithoutProtocol = href
            if (href.startsWith("http://")) {
              hrefWithoutProtocol = href.substring(7)
            }
            if (!hrefWithoutProtocol.contains("javascript:")
              && !hrefWithoutProtocol.contains("@")
              && !hrefWithoutProtocol.contains("mailto:")) {
              val url = URLCanonicalizer.getCanonicalURL(href, webUrl.baseUrl)
              val urlValidator = new UrlValidator(Array("http", "https"))
              if (url != null && urlValidator.isValid(url)) {
                webUrls += new WebUrl(url)
              }
            }
          }
        }
      }

    }
    webPage.webUrls = webUrls
  }

}
