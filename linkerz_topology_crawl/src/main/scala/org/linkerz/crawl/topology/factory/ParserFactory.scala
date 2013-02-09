/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.crawl.topology.parser.{RssParser, LinkerZParser}
import org.linkerz.dao.FeedDao
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class DefaultParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:46 PM
 *
 */

object ParserFactory {

  def createParser() = {
    val feeds = FeedDao.find(MongoDBObject.empty).toList
    new LinkerZParser(feeds)
  }

  def createRssParser() = new RssParser

}
