/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.parser

import org.linkerz.crawler.bot.plugin.Parser
import org.linkerz.mongodb.model.Link
import org.linkerz.crawler.bot.parser.LinkerZParser
import reflect.BeanProperty

/**
 * The Class ParserServiceImpl.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 10:12 PM
 *
 */

class ParserServiceImpl extends ParserService {

  @BeanProperty
  var autoParser: LinkerZParser = _

  def getParser(pluginClass: String): Parser = {
    autoParser.get(pluginClass)
  }
}
