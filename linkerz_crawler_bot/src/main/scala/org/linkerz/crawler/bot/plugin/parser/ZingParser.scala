/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.{ParserData, NewsParser}
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document

/**
 * The Class ZingParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/21/12, 3:11 AM
 *
 */
class ZingParser extends ParserPlugin {

  def pluginData = {
    val pluginData = new ParserPluginData
    pluginData.name = "ZingNews"
    pluginData.version = "0.0.1"
    pluginData.group = "zing.vn"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/news.zing.vn/*/*"
    pluginData.titleSelection = "title"
//    pluginData.descriptionSelection = "h2.pHead"
    pluginData.contentSelection = "#content_document"
    pluginData.imgSelection = "#content_document img"
    pluginData
  }
}
