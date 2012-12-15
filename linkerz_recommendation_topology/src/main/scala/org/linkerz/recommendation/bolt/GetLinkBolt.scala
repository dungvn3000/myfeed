package org.linkerz.recommendation.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.recommendation.event.{GetLink, Start}
import org.linkerz.dao.LinkDao
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class GetNewsBolt.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 2:28 AM
 *
 */
class GetLinkBolt extends StormBolt(outputFields = List("event")) {

  execute {
    tuple => tuple matchSeq {
      case Seq(Start) => {
        val links = LinkDao.find(MongoDBObject.empty).sort(MongoDBObject("indexDate" -> -1)).limit(2000).toList

        links.foreach(link => {
          tuple emit GetLink(link)
        })
      }
    }
  }

}
