package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.event.{PersistentDone, ParseDone}
import org.bson.types.ObjectId
import org.linkerz.dao.NewsDao

/**
 * This bolt is using for persistent data to the database server.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:12 AM
 *
 */
class PersistentBolt extends StormBolt(outputFields = List("feedId", "event")) with Logging {
  execute {
    implicit tuple => tuple matchSeq {
      case Seq(feedId: ObjectId, ParseDone(feed, news)) => {
        NewsDao.save(news)
        tuple.emit(feedId, PersistentDone(news))
        tuple.ack()
      }
    }
  }
}
