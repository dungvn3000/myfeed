/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.session

import org.linkerz.crawl.topology.job.CrawlJob
import grizzled.slf4j.Logging
import util.UUID

/**
 * The Class CrawlSession.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:06 AM
 *
 */
case class CrawlSession(id: UUID, job: CrawlJob) extends Logging {

}
