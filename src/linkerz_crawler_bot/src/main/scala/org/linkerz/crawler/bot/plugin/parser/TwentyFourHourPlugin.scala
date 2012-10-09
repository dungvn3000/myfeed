/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.ParserPlugin
import org.linkerz.mongodb.model.ParserPluginData
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document

/**
 * The Class TwentyFourHourPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 10/4/12 10:28 PM
 *
 */
class TwentyFourHourPlugin extends ParserPlugin {

  def defaultData = {
    val pluginData = new ParserPluginData
    pluginData.name = "24H"
    pluginData.version = "0.0.1"
    pluginData.group = "24h.com.vn"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/*.24h.com.vn/*/*"
    pluginData.titleSelection = ".boxBaiViet-c h1.baiviet-title"
    pluginData.descriptionSelection = ".boxBaiViet-c .baiviet-head-noidung"
    pluginData.imgSelection = ".boxBaiViet-c .baivietMainBox-img200 img"
    pluginData.urlTest = "http://hcm.24h.com.vn/"
    pluginData
  }

  override def beforeParse(crawlJob: CrawlJob, doc: Document): Boolean = {
    if (doc.select(".boxBaiViet-c").isEmpty) {
      crawlJob.code = CrawlJob.SKIP
      crawlJob.info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      return false
    }
    true
  }

}
