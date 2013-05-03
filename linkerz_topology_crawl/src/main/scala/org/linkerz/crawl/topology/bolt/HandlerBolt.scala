package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import collection.JavaConversions._
import grizzled.slf4j.Logging
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event._
import org.linkerz.crawl.topology.session.RichSession._
import java.util.UUID
import org.linkerz.crawl.topology.event.Handle
import org.linkerz.crawl.topology.session.CrawlSession
import org.linkerz.crawl.topology.event.Start
import org.linkerz.crawl.topology.job.FetchJob

/**
 * The mission of this bolt will receive job from the feed spot and emit it to a fetcher. On the other hand this bolt
 * will receive result from parser and decide whether continue crawling or not.
 *
 *
 * @author Nguyen Duc Dung
 * @since 11/29/12 11:41 PM
 *
 */
class HandlerBolt extends StormBolt(outputFields = List("sessionId", "event")) with Logging {

  private var sessions: List[CrawlSession] = Nil

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(sessionId: UUID, Start(parentJob)) => {
        //New sessions.
        val session = CrawlSession(sessionId, parentJob)
        session.queueUrls += parentJob.webUrl
        sessions ::= session
        tuple emit(session.id, Handle(parentJob))
        tuple.ack()
      }
      case Seq(sessionId: UUID, Parse(subJob)) => sessions ~> sessionId map (session => handle(session, subJob)) getOrElse {
        //When session id is none that mean this job is expired already, we will stop it.
        info("Session is expired " + subJob.webUrl)
      }
      case Seq(sessionId: UUID, Ack) => sessions = sessions end sessionId
      case Seq(sessionId: UUID, Fail) => sessions = sessions end sessionId
    }
  }

  private def handle(session: CrawlSession, subJob: FetchJob)(implicit tuple: Tuple) {
    subJob.result.map(webPage => {

      if (subJob.depth > session.currentDepth) {
        session.currentDepth = subJob.depth
      }

      for (webUrl <- webPage.webUrls) {

        //Counting
        session.subJobCount += 1

        //Store queue url.
        session.queueUrls add webUrl

        tuple emit(session.id, Handle(new FetchJob(webUrl, subJob)))
      }
    })

    //Copy logging information from sub jobs.
    session.job.errors ++= subJob.errors
    session.job.infos ++= subJob.infos
    session.job.warns ++= subJob.warns

    tuple.ack()
  }


}
