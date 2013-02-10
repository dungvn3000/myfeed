package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.factory.ParserFactory
import org.linkerz.crawl.topology.model.WebPage

/**
 * This bolt will parse a web page.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:00 AM
 *
 */
class WebPageParserBolt extends StormBolt(outputFields = List("feedId", "webPage")) with Logging {

  @transient
  private val parser = ParserFactory.createWebPageParser()

  execute(implicit tuple => tuple matchSeq {
    case Seq(feedId, webPage: WebPage) => {
      parser.parse(webPage)
      tuple.emit(feedId, webPage)
    }
  })

}
