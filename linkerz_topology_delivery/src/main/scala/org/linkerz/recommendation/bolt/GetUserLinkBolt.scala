package org.linkerz.recommendation.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.recommendation.event.Recommendation
import org.bson.types.ObjectId

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

      }
    }
  }

}
