package org.linkerz.topology.delivery.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.topology.delivery.event.{GetNews, Start}
import org.linkerz.dao.{UserFollowDao, LinkDao, NewBoxDao}
import org.bson.types.ObjectId
import org.linkerz.model.Link
import com.mongodb.casbah.commons.MongoDBObject

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
      val feedIds = UserFollowDao.findUserFollowByUserID(userId).map(_.feedId)
      val lastTime = NewBoxDao.getLastTime(userId)

      val links: List[Link] = if (lastTime.isDefined) {
        LinkDao.getAfter(lastTime.get).filter(link => feedIds.contains(link.feedId))
      } else {
        LinkDao.find(MongoDBObject.empty).toList.filter(link => feedIds.contains(link.feedId))
      }

      if (!links.isEmpty) {
        tuple emit (userId, GetNews(links))
      }

    }
  })

}
