/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.controller

import org.linkerz.job.queue.controller.BaseController
import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class CrawlerController.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:52 AM
 *
 */

class CrawlerController extends BaseController {
  override protected def handleError(job: Job, ex: Exception) {
    super.handleError(job, ex)
    job match {
      case crawlJob: CrawlJob => crawlJob.error(ex.getMessage)
    }
  }
}