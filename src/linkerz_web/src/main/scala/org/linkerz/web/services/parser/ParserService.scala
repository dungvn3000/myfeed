/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.parser

import org.linkerz.crawler.bot.plugin.ParserPlugin
import org.linkerz.mongodb.model.Link
import org.linkerz.crawler.bot.parser.LinkerZParser
import org.linkerz.crawler.bot.factory.ParserPluginFactory

/**
 * The Class ParserService.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 10:12 PM
 *
 */

trait ParserService {
  def parserFactory: ParserPluginFactory
}
