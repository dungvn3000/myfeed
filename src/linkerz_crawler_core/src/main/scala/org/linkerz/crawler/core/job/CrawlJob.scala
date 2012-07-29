/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.job

import org.linkerz.job.queue.core.Job
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class CrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:55 AM
 *
 */

case class CrawlJob(webUrl: WebUrl) extends Job {

  def get() = {
    None
  }

}
