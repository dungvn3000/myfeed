package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.factory.ParserFactory
import org.linkerz.crawl.topology.model.WebPage
import org.linkerz.crawl.topology.parser.WebPageParser
import com.sun.syndication.feed.synd.SyndEntry

/**
 * This bolt will parse a web page.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:00 AM
 *
 */
class WebPageParserBolt extends StormBolt(outputFields = List("feedId", "webPage")) with Logging {

  @transient
  private var parser: WebPageParser = _

  setup {
    parser = ParserFactory.createWebPageParser()
  }

  execute(implicit tuple => tuple matchSeq {
    case Seq(feedId, entry: SyndEntry, webPage: WebPage) => {
      parser.parse(webPage, Some(entry))
      tuple.emit(feedId, webPage)
    }
  })

}
