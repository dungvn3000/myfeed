/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.crawl.topology.parser.LinkerZParser
import org.linkerz.dao.FeedDao
import com.mongodb.casbah.commons.MongoDBObject
import org.linkerz.model.Feed

/**
 * The Class DefaultParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:46 PM
 *
 */

object ParserFactory {
  def createParser(feeds: List[Feed]) = {
    new LinkerZParser(feeds)
  }
}
