package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt

/**
 * This bolt will parse a web page.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:00 AM
 *
 */
class WebPageParserBolt extends StormBolt(outputFields = List("sessionId", "event")) {


}
