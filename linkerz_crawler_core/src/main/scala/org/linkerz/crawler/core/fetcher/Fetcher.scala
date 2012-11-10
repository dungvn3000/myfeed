/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
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

  /**
   * This method will be call after finish download.
   * @param crawlJob
   */
  def parse(crawlJob: CrawlJob)

}