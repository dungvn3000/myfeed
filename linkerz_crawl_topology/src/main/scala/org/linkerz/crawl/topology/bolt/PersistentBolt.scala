package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event.{Handle, Persistent}
import java.util.UUID
import org.linkerz.dao.{LoggingDao, LinkDao}
import java.util.concurrent.TimeoutException

/**
 * This bolt is using for persistent data to the database server.
 *
 * @author Nguyen Duc Dung
 * @since 11/30/12 2:12 AM
 *
 */
class PersistentBolt extends StormBolt(outputFields = List("sessionId", "event")) {
  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, Persistent(job)) => {
        job.result.map {
          webPage => if (!webPage.isError && webPage.parsed) LinkDao.checkAndSave(webPage.asLink)
        }

        //Save error for each job.
        //We will not save TimeOutException, because it so common.
        val errors = job.errors.filter {
          error => !error.message.contains(classOf[TimeoutException].getName)
        }
        LoggingDao.insert(errors)
//        LoggingDao.insert(job.infos)
        LoggingDao.insert(job.warns)

        tuple emit(sessionId, Handle(job))
      }
    }
    tuple.ack()
  }
}
