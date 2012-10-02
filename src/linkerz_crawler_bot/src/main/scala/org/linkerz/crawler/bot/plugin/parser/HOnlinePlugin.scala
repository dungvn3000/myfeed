/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.mongodb.model.ParserPluginData
import org.linkerz.crawler.bot.plugin.ParserPlugin
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document

/**
 * The Class HOnlinePlugin.
 *
 * @author Nguyen Duc Dung
 * @since 9/29/12 10:08 AM
 *
 */
class HOnlinePlugin extends ParserPlugin {

  def defaultData = {
    val pluginData = new ParserPluginData
    pluginData.name = "h-online"
    pluginData.version = "0.0.1"
    pluginData.group = "h-online.com"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/www.h-online.com/*/*"
    pluginData.titleSelection = "#content #item h1"
    pluginData.descriptionSelection = "#content #item .item_wrapper"
    pluginData.descriptionMaxLength = 150
    pluginData.imgSelection = "#content #item .item_wrapper .pic_right img"
    pluginData.urlTest = "http://www.h-online.com/"
    pluginData
  }

  override def beforeParse(crawlJob: CrawlJob, doc: Document): Boolean = {
    if (doc.select("#content #item").isEmpty) {
      crawlJob.code = CrawlJob.SKIP
      crawlJob.info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      return false
    }
    true
  }

}
