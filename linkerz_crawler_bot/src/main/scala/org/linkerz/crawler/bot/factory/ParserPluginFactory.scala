/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.factory

import org.linkerz.crawler.bot.parser.LinkerZParser
import org.linkerz.crawler.core.parser.Parser
import org.linkerz.crawler.core.factory.ParserFactory
import org.linkerz.crawler.bot.plugin.parser._

/**
 * The Class ParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 4:39 AM
 *
 */

class ParserPluginFactory extends ParserFactory {

  override def createParser: Parser = new LinkerZParser(List(
    new GenKParser,
    new HOnlineParser,
    new JavaDZoneParser,
    new TwentyFourHourParser,
    new VnExpressParser,
    new ZingParser
  ))

}
