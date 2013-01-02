package org.linkerz.topology.delivery.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.topology.delivery.event.{GetNews, Start}
import org.linkerz.dao.{LinkDao, NewBoxDao}
import org.bson.types.ObjectId

/**
 * The Class GetNewsBolt.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 12:57 AM
 *
 */
class GetNewsBolt extends StormBolt(outputFields = List("userId", "event")) with Logging {

  execute(tuple => tuple matchSeq {
    case Seq(userId: ObjectId, Start) => {
      NewBoxDao.getLastTime(userId).map(lastTime => {
        val links = LinkDao.getAfter(lastTime)
        if (!links.isEmpty) {
          tuple emit (userId, GetNews(links))
        }
      })
    }
  })

}
