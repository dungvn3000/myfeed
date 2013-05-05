package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.DownloadDone
import org.linkerz.crawl.topology.factory.ParserFactory
import org.linkerz.crawl.topology.parser.NewsParser
import org.bson.types.ObjectId

/**
 * This bolt will parse a web page.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 1:00 AM
 *
 */
class ParserBolt extends StormBolt(outputFields = List("feedId", "event")) {

  @transient
  private var parser: NewsParser = _

  setup {
    parser = ParserFactory.createNewsParser()
  }

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(feedId: ObjectId, DownloadDone(feed, result)) => {
        parser.parse(feedId, result).map(news => {
          tuple.emit(feedId, news)
        })
      }
    }
      tuple.ack()
  }
}
