package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.parser.Parser
import org.linkerz.crawl.topology.factory.ParserFactory

/**
 * This bolt will parse a web page.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:00 AM
 *
 */
class ParserBolt extends StormBolt(outputFields = List("sessionId", "event")) {

  @transient
  private var parser: Parser = _

  setup {
    parser = ParserFactory.createParser()
  }

}
