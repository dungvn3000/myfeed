package org.linkerz.recommendation.bolt

import storm.scala.dsl.StormBolt
import org.bson.types.ObjectId
import org.linkerz.recommendation.Correlation._
import org.linkerz.recommendation.event.MergeLink

/**
 * The Class CorrelationBolt.
 *
 * @author Nguyen Duc Dung
 * @since 12/16/12 3:48 AM
 *
 */
class CorrelationBolt extends StormBolt(outputFields = List("userId", "event")) {

  execute {
    tuple => tuple matchSeq {
      case Seq(userId: ObjectId, MergeLink(clickedLink, link)) => {
        if (clickedLink.text.isDefined && link.text.isDefined) {
          val text1 = clickedLink.text.get
          val text2 = link.text.get
          val score = sim_pearson(text1, text2)
        }
      }
    }
  }

}
