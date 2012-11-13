/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.{ParserData, NewsParser}
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document

/**
 * The Class TwentyFourHourParser.
 *
 * @author Nguyen Duc Dung
 * @since 10/4/12 10:28 PM
 *
 */
class TwentyFourHourParser extends ParserPlugin {

  def pluginData = {
    val pluginData = new ParserPluginData
    pluginData.name = "24H"
    pluginData.version = "0.0.1"
    pluginData.group = "24h.com.vn"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/*.24h.com.vn/*/*"
    pluginData.titleSelection = ".boxBaiViet-c .baiviet-title"
//    pluginData.descriptionSelection = ".boxBaiViet-c .baiviet-head-noidung"
    pluginData.contentSelection = ".boxBaiViet-c"
    pluginData.imgSelection = ".boxBaiViet-c .baivietMainBox-img200 img"
    pluginData
  }

}
