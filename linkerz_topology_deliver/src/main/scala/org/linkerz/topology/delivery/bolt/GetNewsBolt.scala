package org.linkerz.topology.delivery.bolt

import storm.scala.dsl.StormBolt
import grizzled.slf4j.Logging
import org.linkerz.topology.delivery.event.Start
import org.bson.types.ObjectId

/**
 * The Class GetNewsBolt.
 *
 * @author Nguyen Duc Dung
 * @since 1/3/13 12:57 AM
 *
 */
class GetNewsBolt extends StormBolt(outputFields = List("userId", "event")) with Logging {

  execute(tuple => tuple matchSeq {
    case Seq(userId: ObjectId, Start) => {

    }
  })

}
