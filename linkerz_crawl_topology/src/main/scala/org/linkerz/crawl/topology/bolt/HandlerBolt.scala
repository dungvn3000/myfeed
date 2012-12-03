package org.linkerz.crawl.topology.bolt

import storm.scala.dsl.StormBolt
import org.linkerz.crawl.topology.event._
import collection.JavaConversions._
import org.linkerz.crawl.topology.model.WebUrl
import org.linkerz.core.matcher.SimpleRegexMatcher
import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.session.RichSession._
import backtype.storm.tuple.Tuple
import org.linkerz.crawl.topology.event.Ack
import org.linkerz.crawl.topology.event.Handle
import org.linkerz.crawl.topology.session.CrawlSession
import org.linkerz.crawl.topology.event.Start
import org.linkerz.crawl.topology.event.Fetch
import org.apache.commons.lang.StringUtils
import org.linkerz.crawl.topology.job.CrawlJob

/**
 * The mission of this bolt will receive job from the feed spot and emit it to a fetcher. On the other hand this bolt
 * will receive result from parser and decide whether continue crawling or not.
 *
 *
 * @author Nguyen Duc Dung
 * @since 11/29/12 11:41 PM
 *
 */
class HandlerBolt extends StormBolt(outputFields = List("handler")) with Logging {

  private var sessions = List[CrawlSession]()

  execute {
    implicit tuple => tuple matchSeq {
      case Seq(Start(sessionId, parentJob)) => {
        //New sessions.
        val session = CrawlSession(sessionId, parentJob)
        sessions ::= session
        tuple emit Fetch(session.id, parentJob)
        tuple.ack()
      }
      case Seq(Handle(sessionId, subJob)) => sessions ~> sessionId map (session => handle(session, subJob)) getOrElse {
        //When session id is none that mean this job is expired already, we will stop it.
        info("Session is expired " + subJob.webUrl.url)
      }
      case Seq(Ack(sessionId)) => sessions = sessions end sessionId
      case Seq(Fail(sessionId)) => sessions = sessions end sessionId
    }
  }

  private def handle(session: CrawlSession, subJob: CrawlJob)(implicit tuple: Tuple) {
    subJob.result.map(webPage => if (!webPage.isError) {
      session.fetchedUrls add subJob.webUrl
      session.countUrl += webPage.webUrls.size

      //Store result to the database.
//      if (usingDB && LinkDao.checkAndSave(webPage.asLink)) {
//        currentSession.urlStored += 1
//      }

      val crawlJobs = for (webUrl <- webPage.webUrls if (shouldCrawl(session, webUrl))) yield {
        if (subJob.depth > session.currentDepth) {
          session.currentDepth = subJob.depth + 1
        }

        //Counting
        session.subJobCount += 1

        //Store queue url.
        session.queueUrls add subJob.webUrl

        new CrawlJob(webUrl, subJob)
      }

      //Important Note: We don't emit a tuple when changing the session value, to make sure all session when emit have same values.
      crawlJobs foreach (tuple emit Fetch(session.id, _))

    } else if (webPage.isRedirect) {
      val movedUrl = webPage.webUrl.movedToUrl
      if (StringUtils.isNotBlank(movedUrl)) {
        val newWebUrl = new WebUrl(movedUrl)
        if (shouldCrawl(session, newWebUrl)) {
          session.queueUrls add newWebUrl

          tuple emit Fetch(session.id, new CrawlJob(newWebUrl, subJob))
        }
      }
    })

    //Copy logging information from sub jobs.
    session.job.errors ++= subJob.errors
    session.job.infos ++= subJob.infos
    session.job.warns ++= subJob.warns

    tuple.ack()
  }

  private def shouldCrawl(session: CrawlSession, webUrl: WebUrl): Boolean = {

    val job = session.job

    //Step 1: Checking whether go for the job or not
    if (job.maxSubJob >= 0 && session.subJobCount >= job.maxSubJob) {
      return false
    }

    if (session.currentDepth > job.maxDepth && job.maxDepth > 0) {
      return false
    }

    if (job.filterPattern.matcher(webUrl.url).matches()) return false

    //Only crawl the url is match with url regex
    if (job.urlRegex.isDefined && !SimpleRegexMatcher.matcher(webUrl.url, job.urlRegex.get)) {
      return false
    }

    //Not crawl the exclude url
    if (job.excludeUrl != null) job.excludeUrl.foreach(regex => {
      if (SimpleRegexMatcher.matcher(webUrl.url, regex)) {
        return false
      }
    })

    //Only crawl in same domain.
    if (!job.onlyCrawlInSameDomain
      || (job.onlyCrawlInSameDomain && webUrl.domainName == session.domainName)) {
      //Make sure we not fetch a link what we did already.
      if (!session.fetchedUrls.contains(webUrl)) {
        //And make sure the url is not in the queue
        if (!session.queueUrls.contains(webUrl)) {
          return true
        }
      }
    }

    false
  }

}
