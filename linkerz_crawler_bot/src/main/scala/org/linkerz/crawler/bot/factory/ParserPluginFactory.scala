/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.factory

import org.linkerz.crawler.bot.plugin.{ParserPluginData, ParserPlugin}
import collection.mutable.ListBuffer
import collection.JavaConversions._
import org.linkerz.crawler.bot.parser.LinkerZParser
import org.linkerz.crawler.core.parser.Parser
import org.linkerz.crawler.core.factory.ParserFactory

/**
 * The Class ParserFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 4:39 AM
 *
 */

class ParserPluginFactory extends ParserFactory {


  /**
   * Install a plugin
   * @param pluginClass
   * @return
   */
  def install(pluginClass: String): Boolean = {
    true
  }

  /**
   * Delete a plugin
   * @param pluginClass
   */
  def delete(pluginClass: String) {
  }

  override def createParser: Parser = {
    var plugins = new ListBuffer[ParserPlugin]


    new LinkerZParser(plugins.toList)
  }

}
