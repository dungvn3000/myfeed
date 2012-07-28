/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.job

import org.linkerz.job.queue.core.Job

/**
 * The Class CrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:55 AM
 *
 */

case class CrawlJob(url: String) extends Job {

  def get() = {
    None
  }

}
