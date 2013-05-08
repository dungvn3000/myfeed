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
      case Seq(feedId: ObjectId, DownloadDone(feed, item, result)) => {
        try {
          val article = parser.parse(result)
          val news = News(
            url = result.url,
            title = item.getTitle,
            description = Some(item.getDescription),
            text = Some(article.text),
            feedId = feedId
          )
          tuple.emit(feedId, ParseDone(feed, news))
        } catch {
          case ex: Exception => _collector.reportError(ex)
        }
        tuple.ack()
      }
    }
  }
}
