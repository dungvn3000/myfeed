/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.parser

import org.jsoup.nodes.Document
import org.apache.commons.lang.StringUtils
import org.linkerz.crawler.core.job.CrawlJob
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import org.linkerz.crawler.bot.parser.core.{ParserData, NewsParser}

/**
 * The Class VnExpress.net Plugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:57 PM
 *
 */

class VnExpressParser {

//  def pluginData = {
//    val pluginData = new ParserData
//    pluginData.name = "VnExpress"
//    pluginData.version = "0.0.1"
//    pluginData.group = "vnexpress.net"
//    pluginData.pluginClass = this.getClass.getName
//    pluginData.enable = true
//    pluginData.urlRegex = "*/vnexpress.net/*/*/2012/*"
//    pluginData.titleSelection = ".content h1.Title"
////    pluginData.descriptionSelection = ".content .Lead"
//    pluginData.contentSelection = ".content"
//    pluginData.imgSelection = ".content td img"
//    pluginData
//  }

//  override def afterParse(crawlJob: CrawlJob, doc: Document) {
//    val webPage = crawlJob.result.get
//    if (webPage.featureImageUrl.isEmpty || StringUtils.isBlank(webPage.featureImageUrl.get)) {
//      val url = URLCanonicalizer.getCanonicalURL("images/home_selected.gif", webPage.webUrl.baseUrl)
//      if (!StringUtils.isNotBlank(url)) webPage.featureImageUrl = Some(url)
//    }
//
//    super.afterParse(crawlJob, doc)
//  }
}
