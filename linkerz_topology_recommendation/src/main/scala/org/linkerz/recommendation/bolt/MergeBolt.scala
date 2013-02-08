package org.linkerz.recommendation.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.recommendation.event.{MergeLink, GetClickedLink}
import org.linkerz.dao.LinkDao
import org.bson.types.ObjectId
import org.joda.time.DateTime

/**
 * The Class MergeBolt.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 3:12 AM
 *
 */
class MergeBolt extends StormBolt(outputFields = List("userId", "event")) {

  execute {
    tuple => tuple matchSeq {
      case Seq(userId: ObjectId, GetClickedLink(clickedLinks)) => {
        val last7Day = DateTime.now.minusDays(7)
        val links = LinkDao.getAfter(last7Day)
        clickedLinks.foreach(clickedLink => {
          links.foreach(link => {
            tuple emit(userId, MergeLink(clickedLink, link))
          })
        })
      }
    }
  }

}
