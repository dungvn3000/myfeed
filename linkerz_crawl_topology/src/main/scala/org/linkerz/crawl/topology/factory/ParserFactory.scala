/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.dao.NewFeedDao
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.crawl.topology.parser.{CustomParser, AutoDetectParser}

/**
 * The Class DefaultParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:46 PM
 *
 */

object ParserFactory {

  val feed = NewFeedDao.find(MongoDBObject("enable" -> true)).toList
  val parsers = feed.map(CustomParser(_))

  def createParser() = {
    new AutoDetectParser(parsers)
  }
}
