/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.{ParserPluginData, ParserPlugin}
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document

/**
 * The Class GenKPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 9/5/12 11:19 AM
 *
 */
class GenKPlugin extends ParserPlugin {
  def pluginData = {
    val pluginData = new ParserPluginData
    pluginData.name = "Genk"
    pluginData.version = "0.0.1"
    pluginData.group = "genk.vn"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/genk.vn/*/*"
    pluginData.titleSelection = ".news-show .news-showtitle h1 a"
//    pluginData.descriptionSelection = ".news-show .assessment-main1"
//    pluginData.descriptionMaxLength = 150
    pluginData.contentSelection = ".news-show .assessment-main1"
    pluginData.imgSelection = ".news-show .assessment-main1 img"
    pluginData
  }
}
