/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.ParserPlugin
import org.linkerz.mongodb.model.ParserPluginData
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document

/**
 * The Class JavaDzonePlugin.
 *
 * @author Nguyen Duc Dung
 * @since 10/2/12 9:10 PM
 *
 */
class JavaDzonePlugin extends ParserPlugin {

  def defaultData = {
    val pluginData = new ParserPluginData
    pluginData.name = "java dzone"
    pluginData.version = "0.0.1"
    pluginData.group = "dzone.com"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/java.dzone.com/articles/*"
    pluginData.titleSelection = "#article #articleHead h1"
    pluginData.descriptionSelection = "#article .content"
    pluginData.descriptionMaxLength = 150
    pluginData.imgSelection = "#article .content img"
    pluginData.urlTest = "http://java.dzone.com/"
    pluginData
  }

  override def beforeParse(crawlJob: CrawlJob, doc: Document): Boolean = {
    if (doc.select("#article .content").isEmpty) {
      crawlJob.code = CrawlJob.SKIP
      crawlJob.info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      return false
    }
    true
  }

}
