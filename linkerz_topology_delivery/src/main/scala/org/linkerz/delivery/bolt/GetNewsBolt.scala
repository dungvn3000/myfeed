package org.linkerz.delivery.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.delivery.event.{GetNewsDone, Start}
import org.linkerz.dao.{UserFeedDao, UserNewsDao, NewsDao}
import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 * The Class MergeBolt.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 3:12 AM
 *
 */
class GetNewsBolt extends StormBolt(outputFields = List("userId", "event")) {

  execute {
    tuple => tuple matchSeq {
      case Seq(userId: ObjectId, Start) => {
        val userFeedIds = UserFeedDao.getUserFeed(userId).map(_.feedId)
        val last7Day = DateTime.now.minusDays(7)
        val news = NewsDao.getAfter(last7Day, userFeedIds)
        val userClicked = UserNewsDao.getUserClicked(userId)

        news
          .filter(item => UserNewsDao.findByNews(item._id).isEmpty)
          .foreach(item => tuple.emit(userId, GetNewsDone(item, userClicked)))

        tuple.ack()
      }
    }
  }

}
