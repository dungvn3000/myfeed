package org.linkerz.recommendation.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.recommendation.event.{Recommendation, GetClickedLink}
import org.linkerz.dao.NewBoxDao
import org.bson.types.ObjectId

/**
 * The Class GetNewsBolt.
 *
 * @author Nguyen Duc Dung
 * @since 12/15/12 2:28 AM
 *
 */
class GetClickedLinkBolt extends StormBolt(outputFields = List("userId", "event")) {

  execute {
    tuple => tuple matchSeq {
      case Seq(userId: ObjectId, Recommendation) => {
        val clickedLinks = NewBoxDao.getUserClicked(userId)
        clickedLinks.foreach(link => {
          tuple emit GetClickedLink(link)
        })
      }
    }
  }

}
