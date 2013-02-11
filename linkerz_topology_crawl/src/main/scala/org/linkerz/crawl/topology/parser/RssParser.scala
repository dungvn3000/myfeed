package org.linkerz.crawl.topology.parser

import com.sun.syndication.io.{FeedException, SyndFeedInput, XmlReader}
import org.apache.http.{HttpStatus, HttpResponse}
import com.sun.syndication.feed.synd.SyndEntry
import collection.JavaConversions._
import grizzled.slf4j.Logging
import org.linkerz.core.string.RichString._
import org.jsoup.Jsoup

/**
 * The Class RssParser.
 *
 * @author Nguyen Duc Dung
 * @since 2/9/13 10:18 PM
 *
 */
class RssParser extends Logging {

  def parse(response: HttpResponse): List[SyndEntry] = {
    if (response.getStatusLine != null
      && response.getStatusLine.getStatusCode == HttpStatus.SC_OK
      && response.getEntity != null) {
      val xmlReader = new XmlReader(response.getEntity.getContent)
      try {
        val feed = new SyndFeedInput().build(xmlReader)
        if (!feed.getEntries.isEmpty) {
          val entries = feed.getEntries.map(_.asInstanceOf[SyndEntry]).toList
          entries.foreach(entry => {
            if (entry.getDescription != null) {
              val html = entry.getDescription.getValue
              if (html != null && html.isNotBlank) {
                val doc = Jsoup.parse(html)
              }
            }
          })
          return entries
        }
      } catch {
        case ex: FeedException => error(ex.getMessage, ex)
      } finally {
        xmlReader.close()
      }
    }
    Nil
  }

}
