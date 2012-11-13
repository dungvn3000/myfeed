/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.parser

import org.linkerz.crawler.bot.parser.core.{ParserData, NewsParser}

/**
 * The Class TwentyFourHourParser.
 *
 * @author Nguyen Duc Dung
 * @since 10/4/12 10:28 PM
 *
 */
class TwentyFourHourParser extends NewsParser {

  def pluginData = {
    val pluginData = new ParserData
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
