/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.job

import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class NewFeedJob.
 *
 * @author Nguyen Duc Dung
 * @since 9/4/12 10:41 AM
 *
 */
class NewFeedJob(url: String) extends CrawlJob(new WebUrl(url)) {
}
