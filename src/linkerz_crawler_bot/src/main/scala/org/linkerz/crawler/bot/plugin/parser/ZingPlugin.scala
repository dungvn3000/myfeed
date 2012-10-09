/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.ParserPlugin
import org.linkerz.mongodb.model.ParserPluginData
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document

/**
 * The Class ZingPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/21/12, 3:11 AM
 *
 */
class ZingPlugin extends ParserPlugin {

  def defaultData = {
    val pluginData = new ParserPluginData
    pluginData.name = "ZingNews"
    pluginData.version = "0.0.1"
    pluginData.group = "zing.vn"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/news.zing.vn/*/*"
    pluginData.titleSelection = "title"
    pluginData.descriptionSelection = "h2.pHead"
    pluginData.imgSelection = "#content_document img"
    pluginData.urlTest = "http://news.zing.vn/"
    pluginData
  }

  override def beforeParse(crawlJob: CrawlJob, doc: Document): Boolean = {
    if (doc.select("#content_document").isEmpty) {
      crawlJob.code = CrawlJob.SKIP
      crawlJob.info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      return false
    }
    true
  }
}
