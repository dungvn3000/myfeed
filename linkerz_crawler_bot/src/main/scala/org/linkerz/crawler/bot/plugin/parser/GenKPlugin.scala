/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.ParserPlugin
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document
import org.linkerz.model.ParserPluginData

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

  override def beforeParse(crawlJob: CrawlJob, doc: Document): Boolean = {
    if (doc.select(".assessment-main1").isEmpty) {
      crawlJob.code = CrawlJob.SKIP
      crawlJob.info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      return false
    }
    true
  }
}
