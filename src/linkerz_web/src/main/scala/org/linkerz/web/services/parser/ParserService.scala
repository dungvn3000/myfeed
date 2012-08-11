/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.services.parser

import org.linkerz.crawler.bot.plugin.Parser
import org.linkerz.mongodb.model.Link

/**
 * The Class ParserService.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 10:12 PM
 *
 */

trait ParserService {

  /**
   * Return correct parser.
   * @param pluginClass
   */
  def getParser(pluginClass: String): Parser

}