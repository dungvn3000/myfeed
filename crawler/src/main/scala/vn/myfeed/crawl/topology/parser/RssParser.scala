package vn.myfeed.crawl.topology.parser

import grizzled.slf4j.Logging
import java.io.ByteArrayInputStream
import vn.myfeed.crawl.topology.downloader.DownloadResult
import com.sun.syndication.io.{XmlReader, SyndFeedInput}
import com.sun.syndication.feed.synd.SyndFeed

/**
 * The Class RssParser.
 *
 * @author Nguyen Duc Dung
 * @since 5/4/13 6:09 AM
 *
 */
class RssParser extends Logging {

  def parse(result: DownloadResult): SyndFeed = {
    val input = new ByteArrayInputStream(result.content)
    val feedInput = new SyndFeedInput
    val feed = feedInput.build(new XmlReader(input))
    input.close()
    feed
  }

}
