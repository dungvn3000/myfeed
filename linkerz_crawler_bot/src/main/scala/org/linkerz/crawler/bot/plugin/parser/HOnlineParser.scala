/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.{ParserData, NewsParser}
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document

/**
 * The Class HOnlineParser.
 *
 * @author Nguyen Duc Dung
 * @since 9/29/12 10:08 AM
 *
 */
class HOnlineParser extends NewsParser {

  def pluginData = {
    val pluginData = new ParserData
    pluginData.name = "h-online"
    pluginData.version = "0.0.1"
    pluginData.group = "h-online.com"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/www.h-online.com/*/*"
    pluginData.titleSelection = "#content #item h1"
//    pluginData.descriptionSelection = "#content #item .item_wrapper"
//    pluginData.descriptionMaxLength = 150
    pluginData.imgSelection = "#content #item .item_wrapper .pic_right img"
    pluginData
  }

}
