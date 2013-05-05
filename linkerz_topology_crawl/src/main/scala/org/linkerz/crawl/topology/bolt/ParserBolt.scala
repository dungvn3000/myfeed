package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{ParseDone, DownloadDone}
import org.linkerz.crawl.topology.factory.ParserFactory
import org.linkerz.crawl.topology.parser.NewsParser
import org.bson.types.ObjectId
import org.linkerz.model.News

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
        parser.parse(result).map(article => {

          val news = News(
            url = result.getFetchedUrl,
            title = article.title,
            description = Some(article.description()),
            text = Some(article.text),
            feedId = feedId
          )
          tuple.emit(feedId, ParseDone(feed, news))
        })
      }
    }
      tuple.ack()
  }
}
