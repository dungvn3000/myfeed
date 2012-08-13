/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.session

import org.linkerz.job.queue.core.Session
import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class CrawlSession.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:06 AM
 *
 */

class CrawlSession extends Session[CrawlJob] {

  def openSession(job: CrawlJob) = null

  def endSession() {}
}
