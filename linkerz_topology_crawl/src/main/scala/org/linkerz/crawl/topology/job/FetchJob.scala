/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.job

import org.linkerz.model.Feed
import crawlercommons.fetcher.FetchedResult

/**
 * The Class FetchJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 12:55 AM
 *
 */

case class FetchJob(feed: Feed) {

  var result : FetchedResult = _

  def url = feed.url

}