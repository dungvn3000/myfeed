/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.job

import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.model.NewFeed

/**
 * The Class FeedJob.
 *
 * @author Nguyen Duc Dung
 * @since 9/4/12 10:41 AM
 *
 */
case class FeedJob(newFeed: NewFeed) extends CrawlJob(new WebUrl(newFeed.url)) {

  if(!newFeed.urlRegex.isEmpty) {
    urlRegex = Some(newFeed.urlRegex)
  }

  if(!newFeed.excludeUrl.isEmpty) {
    excludeUrl = newFeed.excludeUrl
  }

}
