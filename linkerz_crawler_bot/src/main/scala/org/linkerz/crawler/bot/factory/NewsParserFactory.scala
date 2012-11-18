/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.factory

import org.linkerz.crawler.core.parser.Parser
import org.linkerz.crawler.core.factory.ParserFactory
import org.linkerz.crawler.bot.parser._
import core.{LinkerZParser, NewsParser}
import org.linkerz.model.NewFeedDao
import com.mongodb.casbah.commons.MongoDBObject

/**
 * The Class ParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 4:39 AM
 *
 */

class NewsParserFactory extends ParserFactory {

  override def createParser(): Parser = {
    val feed = NewFeedDao.find(MongoDBObject("enable" -> true))
    val parsers = feed.map(NewsParser(_))
    new LinkerZParser(parsers.toList)
  }

}
