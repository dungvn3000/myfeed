package org.linkerz.crawl.topology.parser

import com.sun.syndication.io.{XmlReader, SyndFeedInput, FeedException}
import com.sun.syndication.feed.synd.SyndEntry
import collection.JavaConversions._
import grizzled.slf4j.Logging
import java.io.ByteArrayInputStream
import org.linkerz.crawl.topology.downloader.DownloadResult

/**
 * The Class RssParser.
 *
 * @author Nguyen Duc Dung
 * @since 5/4/13 6:09 AM
 *
 */
class RssParser extends Logging {

  def parse(result: DownloadResult): List[SyndEntry] = {
    val input = new ByteArrayInputStream(result.content)
    val xmlReader = new XmlReader(input)
    try {
      val feed = new SyndFeedInput().build(xmlReader)
      if (!feed.getEntries.isEmpty) {
        return feed.getEntries.map(_.asInstanceOf[SyndEntry]).toList
      }
    } catch {
      case ex: FeedException => error(ex.getMessage, ex)
    } finally {
      xmlReader.close()
      input.close()
    }
    Nil
  }

}
