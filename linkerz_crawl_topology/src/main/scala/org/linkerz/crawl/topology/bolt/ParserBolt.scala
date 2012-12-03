package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.{MetaFetch, Parse}
import org.linkerz.crawl.topology.parser.Parser
import org.linkerz.crawl.topology.factory.ParserFactory

/**
 * This bolt will parse a web page.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:00 AM
 *
 */
class ParserBolt extends StormBolt(outputFields = List("parse")) {

  var parser: Parser = _

  setup {
    parser = ParserFactory.createParser()
  }

  override def execute(tuple: Tuple) {
    tuple matchSeq {
      case Seq(Parse(sessionId, job)) => {
        try {
          if(job.result.exists(!_.isError)) parser parse job
        } catch {
          case ex: Exception => {
            job.error(ex.getMessage, getClass.getName, job.webUrl, ex)
            _collector reportError ex
          }
        }

        tuple emit MetaFetch(sessionId, job)
      }
    }
    tuple.ack()
  }
}
