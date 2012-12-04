/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.dao.NewFeedDao
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.crawl.topology.parser.{CustomParser, AutoParser}

/**
 * The Class DefaultParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:46 PM
 *
 */

object ParserFactory {

  lazy val feed = NewFeedDao.find(MongoDBObject("enable" -> true))
  lazy val parsers = feed.map(CustomParser(_))

  def createParser() = {
    new AutoParser(parsers.toList)
  }
}
