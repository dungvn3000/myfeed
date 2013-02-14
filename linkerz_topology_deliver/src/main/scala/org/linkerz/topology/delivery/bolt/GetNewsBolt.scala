package org.linkerz.topology.delivery.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.topology.delivery.event.{GetNews, Start}
import org.bson.types.ObjectId
import org.linkerz.dao.{NewsBoxDao, LinkDao}
import org.joda.time.DateTime

/**
 * The Class GetNewsBolt.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 12:57 AM
 *
 */
class GetNewsBolt extends StormBolt(outputFields = List("userId", "event")) with Logging {

  execute(tuple => tuple matchSeq {
    case Seq(userId: ObjectId, Start(feedIds)) => {
      val last7Day = DateTime.now.minusDays(7)
      var links = LinkDao.getAfter(last7Day, feedIds)

      links = links.filter(link => {
        !NewsBoxDao.isExist(link._id, userId)
      })

      if (!links.isEmpty) {
        tuple emit(userId, GetNews(links))
      }
    }
  })

}
