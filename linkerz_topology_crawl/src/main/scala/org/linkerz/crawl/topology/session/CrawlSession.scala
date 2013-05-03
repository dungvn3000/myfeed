/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.session

import org.linkerz.crawl.topology.job.FetchJob
import org.linkerz.crawl.topology.model.WebUrl
import java.util
import grizzled.slf4j.Logging
import util.UUID

/**
 * The Class CrawlSession.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:06 AM
 *
 */
case class CrawlSession(id: UUID, job: FetchJob) extends Logging {

  /**
   * Store queue urls list, It is not using for queue url,
   * it is using for mark the url already queue then we won't queue again.
   */
  val queueUrls = new util.HashSet[WebUrl]()

  var currentDepth = 0

  //Counting child jobs was done by the current job.
  var subJobCount = 0

  //Starting count the time on current job.
  val startTime = System.currentTimeMillis

  /**
   * Time for done the job.
   * @return
   */
  def jobTime = System.currentTimeMillis - startTime

  var domainName: String = job.webUrl.domainName

  def endSession() {
    info("End session " + domainName + " " + (jobTime / 1000) + " s")
    info(subJobCount + " links downloaded")
    info(currentDepth + " level")
    info(job.errors.length + " errors found")
  }
}
