/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin.zing

import org.linkerz.crawler.bot.plugin.ParserPlugin
import org.linkerz.mongodb.model.ParserPluginData
import grizzled.slf4j.Logging

/**
 * The Class ZingPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/21/12, 3:11 AM
 *
 */
class ZingPlugin extends ParserPlugin with Logging {

  def defaultData = {
    val pluginData = new ParserPluginData
    pluginData.name = "ZingNews"
    pluginData.version = "0.0.1"
    pluginData.group = "zing.vn"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/news.zing.vn/*/*"
    pluginData.titleSelection = "h1.pTitle"
    pluginData.descriptionSelection = "h2.pHead"
    pluginData.imgSelection = "#content_document img"
    pluginData.urlTest = "http://news.zing.vn/"
    pluginData
  }


}
