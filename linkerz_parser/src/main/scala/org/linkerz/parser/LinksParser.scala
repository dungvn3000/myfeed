package org.linkerz.parser

import org.jsoup.nodes.Document
import org.apache.commons.lang.StringUtils
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import org.apache.commons.validator.routines.UrlValidator
import collection.JavaConversions._
import org.apache.http.client.utils.URIUtils
import java.net.URI

/**
 * This class using for crawling.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:35 PM
 *
 */
class LinksParser {

  def parse(doc: Document) = {
    //Using java list for better performance.
    var webUrls = new java.util.HashSet[String]
    val httpHost = URIUtils.extractHost(new URI(doc.baseUri()))
    val baseUrl = httpHost.toURI

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
            val url = URLCanonicalizer.getCanonicalURL(href, baseUrl)
            val urlValidator = new UrlValidator(Array("http", "https"))
            if (url != null && urlValidator.isValid(url)) {
              webUrls += url
            }
          }
        }
      }
    }
    webUrls
  }

}
