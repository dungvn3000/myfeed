package org.linkerz.recommendation.spout

import grizzled.slf4j.Logging
import backtype.storm.utils.Utils
import org.linkerz.dao.UserDao
import com.mongodb.casbah.commons.MongoDBObject
import storm.scala.dsl.StormSpout
import org.linkerz.recommendation.event.Recommendation

/**
 * The Class RecommendQueueSpout.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 2:27 AM
 *
 */
class RecommendQueueSpout extends StormSpout(outputFields = List("userId", "event")) with Logging {
  def nextTuple() {
    val users = UserDao.find(MongoDBObject.empty).toList
    users.foreach(user =>
      emit(user._id, Recommendation)
    )
    //Sleep 15 minutes.
    Utils sleep 1000 * 60 * 15
  }
}
