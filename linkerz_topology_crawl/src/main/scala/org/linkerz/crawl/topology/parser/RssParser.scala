package org.linkerz.crawl.topology.parser

import com.sun.syndication.io.{XmlReader, SyndFeedInput, FeedException}
import com.sun.syndication.feed.synd.SyndEntry
import org.apache.http.HttpStatus
import collection.JavaConversions._
import grizzled.slf4j.Logging
import java.io.ByteArrayInputStream
import org.linkerz.crawl.topology.job.FetchJob

/**
 * The Class RssParser.
 *
 * @author Nguyen Duc Dung
 * @since 5/4/13 6:09 AM
 *
 */
class RssParser extends Logging {

  def parse(job: FetchJob): List[SyndEntry] = {
    val result = job.result
    if (result.getStatusCode == HttpStatus.SC_OK
      && result.getContentLength > 0) {
      val input = new ByteArrayInputStream(result.getContent)
      val xmlReader = new XmlReader(input)
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
        input.close()
      }
    }
    Nil
  }

}
