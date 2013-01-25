package org.linkerz.topology.delivery.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.bson.types.ObjectId
import org.linkerz.topology.delivery.event.GetNews

/**
 * The Class DeliveryBolt.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 12:57 AM
 *
 */
class DeliveryBolt extends StormBolt(outputFields = List("userId", "event")) with Logging {

  execute(tuple => tuple matchSeq {
    case Seq(userId: ObjectId, GetNews(links)) => {

    }
  })

}
