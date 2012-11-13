package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.{ParserData, NewsParser}

/**
 * The Class ActionVnParser.
 *
 * @author Nguyen Duc Dung
 * @since 11/13/12 8:10 PM
 *
 */
class ActionVnParser extends ParserPlugin {

  def pluginData = {
    val pluginData = new ParserPluginData
    pluginData.name = "action.vn"
    pluginData.version = "0.0.1"
    pluginData.group = "startup"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/action.vn/*"
    pluginData.titleSelection = ".contentpaneopen h1.contentheading"
    pluginData.contentSelection = ".contentpaneopenlink"
    pluginData.imgSelection = ".contentpaneopenlink img"
    pluginData
  }

}
