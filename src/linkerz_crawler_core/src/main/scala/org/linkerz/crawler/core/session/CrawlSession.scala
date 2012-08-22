/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.session

import org.linkerz.job.queue.core.Session
import org.linkerz.crawler.core.job.CrawlJob
import collection.mutable
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class CrawlSession.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:06 AM
 *
 */

class CrawlSession extends Session[CrawlJob] {

  var crawlTime: Long = 0
  var countUrl = 0
  var currentDepth = 0

  /**
   * Store fetched urls list
   */
  var fetchedUrls = mutable.HashSet.empty[WebUrl]

  var domainName: String = _

  def openSession(job: CrawlJob) = {
    this.job = job
    domainName = job.webUrl.domainName
    this
  }
}
