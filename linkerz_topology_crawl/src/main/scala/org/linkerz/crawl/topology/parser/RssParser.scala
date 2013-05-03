package org.linkerz.crawl.topology.parser

import com.sun.syndication.io.{XmlReader, SyndFeedInput, FeedException}
import com.sun.syndication.feed.synd.SyndEntry
import org.apache.http.{HttpResponse, HttpStatus}
import collection.JavaConversions._
import grizzled.slf4j.Logging

/**
 * The Class RssParser.
 *
 * @author Nguyen Duc Dung
 * @since 5/4/13 6:09 AM
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
