package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{MetaFetch, Persistent}
import java.util.UUID
import org.linkerz.dao.{LoggingDao, LinkDao}
import grizzled.slf4j.Logging

/**
 * This bolt is using for persistent data to the database server.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:12 AM
 *
 */
class PersistentBolt extends StormBolt(outputFields = List("sessionId", "event")) with Logging {
  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, MetaFetch(job)) => {
        job.result.map {
          webPage => if (!webPage.isError) {
            val link = LinkDao.checkAndSave(webPage.asLink)
          }
        }

        //Save error for each job.
        //We will not save TimeOutException, because it so common.
        if (!job.errors.isEmpty) {
          LoggingDao.insert(job.errors)
        }
        //LoggingDao.insert(job.infos)
        //LoggingDao.insert(job.warns)

        tuple emit(sessionId, Persistent(job))
      }
    }
    tuple.ack()
  }
}
