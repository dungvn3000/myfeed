package org.linkerz.parser

import model.Link
import org.jsoup.nodes.Document
import org.apache.commons.lang.StringUtils
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import org.apache.commons.validator.routines.UrlValidator
import collection.JavaConversions._
import collection.JavaConverters._
import org.apache.http.client.utils.URIUtils
import java.net.URI
import collection.mutable.ListBuffer

/**
 * This class using for crawling.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:35 PM
 *
 */
class LinksParser {

  /**
   *
   * @param doc
   * @return Duplicate urls will be removed.
   */
  def parse(doc: Document) = {
    //Using java list for better performance.
    val containImageLinks = new ListBuffer[Link]()
    val notContainImageLinks = new ListBuffer[Link]()
    val links = new ListBuffer[Link]()

    val httpHost = URIUtils.extractHost(new URI(doc.baseUri()))
    val baseUrl = httpHost.toURI

    //Step 1: extract links form a web page. We split the list become two for scoring later on.
    val urlValidator = new UrlValidator(Array("http", "https"))
    val linkElements = doc.select("a")
    linkElements.foreach {
      linkElement => {
        var href = linkElement.attr("href")
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
            if (url != null && urlValidator.isValid(url)) {
              val newLink = Link(url, linkElement)
              if (!containImageLinks.contains(newLink) && !notContainImageLinks.contains(newLink)) {
                if (newLink.isContainImage) {
                  containImageLinks += newLink
                } else {
                  notContainImageLinks += newLink
                }
              }
            }
          }
        }
      }
    }

    //Step 2: Merge two list which links contain image will be on the top.
    links ++= containImageLinks
    links ++= notContainImageLinks

    //Step 3: Scoring with the order of link.
    for (i <- 0 until links.size) {
      val link = links(i)
      link.score = (links.size - i).toDouble / links.size
    }

    links.toList.sortBy(-_.score)
  }

  /**
   * For Java Api
   * @param doc
   * @return
   */
  def parseToJavaList(doc: Document) = parse(doc).asJava

}
