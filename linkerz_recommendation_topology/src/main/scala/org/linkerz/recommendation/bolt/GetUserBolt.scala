package org.linkerz.recommendation.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.recommendation.event.{GetUser, Start}
import org.linkerz.dao.UserDao
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class GetUserBolt.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 2:27 AM
 *
 */
class GetUserBolt extends StormBolt(outputFields = List("event")) {

  execute {
    tuple => tuple matchSeq {
      case Start => {
        val users = UserDao.find(MongoDBObject.empty).toList
        users.foreach(user =>
          tuple emit GetUser(user)
        )
      }
    }
  }

}
