package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import java.util.UUID
import org.linkerz.crawl.topology.event.{Ratting, MetaFetch}

/**
 * The Class RattingBolt.
 *
 * @author Nguyen Duc Dung
 * @since 1/25/13 11:07 PM
 *
 */
class RattingBolt extends StormBolt(outputFields = List("sessionId", "event")) {

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, MetaFetch(job)) => {
        try {
          if (job.result.exists(!_.isError)) {
            //Ratting the link

          }
        } catch {
          case ex: Exception => {
            job.error(ex.getMessage, getClass.getName, job.webUrl, ex)
            _collector reportError ex
          }
        }

        tuple emit(sessionId, Ratting(job))
      }
    }

    tuple.ack()
  }

}
