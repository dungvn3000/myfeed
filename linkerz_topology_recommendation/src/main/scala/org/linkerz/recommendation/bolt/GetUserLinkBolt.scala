package org.linkerz.recommendation.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.recommendation.event.{Recommendation, GetUserLink}
import org.linkerz.dao.{UserLinkDao, NewsBoxDao}
import org.bson.types.ObjectId
import org.linkerz.model.UserLink

/**
 * The Class GetUserLinkBolt.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 2:28 AM
 *
 */
class GetUserLinkBolt extends StormBolt(outputFields = List("userId", "event")) {

  execute {
    tuple => tuple matchSeq {
      case Seq(userId: ObjectId, Recommendation) => {
        val userLinks = UserLinkDao.findByUserId(userId)
        tuple emit(userId, GetUserLink(userLinks))
      }
    }
  }

}
