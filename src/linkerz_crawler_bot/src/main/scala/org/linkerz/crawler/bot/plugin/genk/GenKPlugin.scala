/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin.genk

import org.linkerz.crawler.bot.plugin.ParserPlugin
import org.linkerz.mongodb.model.ParserPluginData

/**
 * The Class GenKPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 9/5/12 11:19 AM
 *
 */
class GenKPlugin extends ParserPlugin {
  def defaultData = {
    val pluginData = new ParserPluginData
    pluginData.name = "Genk"
    pluginData.version = "0.0.1"
    pluginData.group = "genk.vn"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/genk.vn/*/*"
    pluginData.titleSelection = ".news-show .news-showtitle h1 a"
    pluginData.descriptionSelection = ".news-show .assessment-main1"
    pluginData.descriptionMaxLength = 150
    pluginData.imgSelection = ".news-show .assessment-main1 img"
    pluginData.urlTest = "http://genk.vn/"
    pluginData
  }
}
