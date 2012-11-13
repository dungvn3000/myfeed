/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin.parser

import org.linkerz.crawler.bot.plugin.{ParserPluginData, ParserPlugin}
import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document
import org.apache.commons.lang.StringUtils

/**
 * The Class JavaDZonePlugin.
 *
 * @author Nguyen Duc Dung
 * @since 10/2/12 9:10 PM
 *
 */
class JavaDZonePlugin extends ParserPlugin {

  def pluginData = {
    val pluginData = new ParserPluginData
    pluginData.name = "java dzone"
    pluginData.version = "0.0.1"
    pluginData.group = "dzone.com"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/java.dzone.com/articles/*"
    pluginData.titleSelection = "#article #articleHead h1"
//    pluginData.descriptionSelection = "#article .content"
//    pluginData.descriptionMaxLength = 150
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

  override def afterParse(crawlJob: CrawlJob, doc: Document) {
    val webPage = crawlJob.result.get
    if (webPage.featureImageUrl.isEmpty || StringUtils.isBlank(webPage.featureImageUrl.get)) {
      //Set default image if the article has no image.
      webPage.featureImageUrl = Some("http://java.dzone.com/sites/all/themes/dzone2012/images/mh_dzone_logo.jpg")
    }
    super.afterParse(crawlJob, doc)
  }
}
