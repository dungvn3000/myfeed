/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.job

import org.linkerz.crawler.core.job.CrawlJob
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.model.NewFeed

/**
 * The Class NewFeedJob.
 *
 * @author Nguyen Duc Dung
 * @since 9/4/12 10:41 AM
 *
 */
case class NewFeedJob(newFeed: NewFeed) extends CrawlJob(new WebUrl(newFeed.url)) {

  if(!newFeed.regex.isEmpty) {
    urlRegex = newFeed.sRegex
  }

  if(!newFeed.ignore.isEmpty) {
    excludeUrl = newFeed.sIgnore
  }

}
