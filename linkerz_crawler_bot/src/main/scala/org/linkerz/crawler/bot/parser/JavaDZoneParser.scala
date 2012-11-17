/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.parser

import org.linkerz.crawler.core.job.CrawlJob
import org.jsoup.nodes.Document
import org.apache.commons.lang.StringUtils
import org.linkerz.crawler.bot.parser.core.{ParserData, NewsParser}

/**
 * The Class JavaDZoneParser.
 *
 * @author Nguyen Duc Dung
 * @since 10/2/12 9:10 PM
 *
 */
class JavaDZoneParser {

//  def pluginData = {
//    val pluginData = new ParserData
//    pluginData.name = "java dzone"
//    pluginData.version = "0.0.1"
//    pluginData.group = "dzone.com"
//    pluginData.pluginClass = this.getClass.getName
//    pluginData.enable = true
//    pluginData.urlRegex = "*/java.dzone.com/articles/*"
//    pluginData.titleSelection = "#article #articleHead h1"
////    pluginData.descriptionSelection = "#article .content"
////    pluginData.descriptionMaxLength = 150
//    pluginData.imgSelection = "#article .content img"
//    pluginData
//  }

//  override def afterParse(crawlJob: CrawlJob, doc: Document) {
//    val webPage = crawlJob.result.get
//    if (webPage.featureImageUrl.isEmpty || StringUtils.isBlank(webPage.featureImageUrl.get)) {
//      //Set default image if the article has no image.
//      webPage.featureImageUrl = Some("http://java.dzone.com/sites/all/themes/dzone2012/images/mh_dzone_logo.jpg")
//    }
//    super.afterParse(crawlJob, doc)
//  }
}
