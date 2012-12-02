/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.session

import org.linkerz.job.queue.core.Session
import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.model.WebUrl
import java.util
import grizzled.slf4j.Logging

/**
 * The Class CrawlSession.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:06 AM
 *
 */

class CrawlSession extends Session[CrawlJob] with Logging {

  var countUrl = 0

  var urlStored = 0

  /**
   * Store fetched urls list
   */
  val fetchedUrls = new util.HashSet[WebUrl]()

  /**
   * Store queue urls list, It is not using for queue url,
   * it is using for mark the url already queue then we won't queue again.
   */
  val queueUrls = new util.HashSet[WebUrl]()

  var domainName: String = _

  def openSession(job: CrawlJob) = {
    this.job = job
    domainName = job.webUrl.domainName
    this
  }

  override def endSession() {
    info("Crawl " + domainName + " " + jobTime + " ms")
    info(countUrl + " links found")
    info(fetchedUrls.size + " links downloaded")
    info(urlStored + " links stored in db")
    info(currentDepth + " level")
    info(job.errors.length + " errors found")
  }
}
