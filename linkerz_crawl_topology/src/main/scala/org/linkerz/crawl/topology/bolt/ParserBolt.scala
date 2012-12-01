package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{MetaFetch, Parse}
import org.linkerz.crawl.topology.factory.{DefaultParserFactory, ParserFactory}
import org.linkerz.crawl.topology.parser.Parser

/**
 * This bolt will parse a web page.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:00 AM
 *
 */
class ParserBolt extends StormBolt(outputFields = List("parse")) {

  var parserFactory: ParserFactory = _
  var parser: Parser = _

  setup {
    parserFactory = new DefaultParserFactory
    parser = parserFactory.createParser()
  }

  override def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(Parse(session, job)) => {
        parser parse job
        tuple emit MetaFetch(session, job)
      }
    }
    tuple.ack
  }
}
