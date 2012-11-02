/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core.parser

import grizzled.slf4j.Logging
import org.linkerz.crawler.core.job.CrawlJob


/**
 * The Class Parser.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:09 AM
 *
 */

trait Parser extends Logging {

  def parse(crawlJob: CrawlJob)

}