/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core.session

import org.linkerz.job.queue.core.Session
import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.model.WebUrl
import gnu.trove.set.hash.THashSet

/**
 * The Class CrawlSession.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:06 AM
 *
 */

class CrawlSession extends Session[CrawlJob] {

  var countUrl = 0

  /**
   * Store fetched urls list
   */
  var fetchedUrls = new THashSet[WebUrl]()

  /**
   * Store queue urls list, It is not using for queue url,
   * it is using for mark the url already queue then we won't queue again.
   */
  var queueUrls = new THashSet[WebUrl]()

  var domainName: String = _

  def openSession(job: CrawlJob) = {
    this.job = job
    domainName = job.webUrl.domainName
    this
  }
}