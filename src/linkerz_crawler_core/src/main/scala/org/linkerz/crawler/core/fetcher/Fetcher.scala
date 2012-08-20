/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.fetcher

import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class Fetcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/1/12, 1:52 AM
 *
 */

trait Fetcher {

  /**
   * Fetch a url
   * @param crawlJob
   */
  def fetch(crawlJob: CrawlJob)

}
