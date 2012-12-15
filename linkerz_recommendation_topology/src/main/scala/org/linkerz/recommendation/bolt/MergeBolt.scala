package org.linkerz.recommendation.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.recommendation.event.{MergeLink, GetClickedLink}
import org.linkerz.dao.LinkDao
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId

/**
 * The Class MergeBolt.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 3:12 AM
 *
 */
class MergeBolt extends StormBolt(outputFields = List("userId","event")) {

  execute {
    tuple => tuple matchSeq {
      case Seq(userId: ObjectId, GetClickedLink(clickedLink)) => {
        val links = LinkDao.find(MongoDBObject.empty).toList
        links.foreach(link => {
          tuple emit(userId, MergeLink(clickedLink, link))
        })
      }
    }
  }

}
