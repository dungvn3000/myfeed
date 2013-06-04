package vn.myfeed.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import vn.myfeed.crawl.topology.event.{ParseDone, DownloadDone}
import vn.myfeed.crawl.topology.factory.ParserFactory
import vn.myfeed.crawl.topology.parser.NewsParser
import org.bson.types.ObjectId
import vn.myfeed.model.News
import ch.sentric.URL
import org.apache.commons.lang3.StringUtils

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
          val url = new URL(result.url)
          val title = if(StringUtils.isNotBlank(item.getTitle)) item.getTitle else article.title
          if(StringUtils.isNotBlank(title)) {
            val news = News(
              _id = url.getNormalizedUrl,
              url = result.url,
              title = title,
              description = Some(item.getDescription),
              text = Some(article.text),
              feedId = feedId
            )
            tuple.emit(feedId, ParseDone(feed, news))
          }
        } catch {
          case ex: Exception => _collector.reportError(ex)
        }
        tuple.ack()
      }
    }
  }
}
