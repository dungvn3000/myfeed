/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.parser

import grizzled.slf4j.Logging
import org.linkerz.crawl.topology.job.FetchJob


/**
 * The Class Parser.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:09 AM
 *
 */

trait Parser extends Logging {

  def parse(crawlJob: FetchJob)

}