package org.linkerz.crawl.topology.parser

import grizzled.slf4j.Logging
import java.io.ByteArrayInputStream
import org.linkerz.crawl.topology.downloader.DownloadResult
import org.horrabin.horrorss.RssFeed

/**
 * The Class RssParser.
 *
 * @author Nguyen Duc Dung
 * @since 5/4/13 6:09 AM
 *
 */
class RssParser extends Logging {

  def parse(result: DownloadResult): RssFeed = {
    val horrorssParser = new org.horrabin.horrorss.RssParser
    val input = new ByteArrayInputStream(result.content)
    val feed = horrorssParser.load(input)
    input.close()
    feed
  }

}
