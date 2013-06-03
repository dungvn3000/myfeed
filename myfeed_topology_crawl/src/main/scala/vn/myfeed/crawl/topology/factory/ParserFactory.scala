/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package vn.myfeed.crawl.topology.factory

import vn.myfeed.crawl.topology.parser.{RssParser, NewsParser}

/**
 * The Class DefaultParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:46 PM
 *
 */

object ParserFactory {
  def createNewsParser() = new NewsParser()

  def createRssParser() = new RssParser
}
