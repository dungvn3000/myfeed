/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin.vnexpress

import grizzled.slf4j.Logging
import org.linkerz.mongodb.model.{ParserPlugin, Link}
import org.jsoup.nodes.Document
import org.linkerz.crawler.bot.plugin.{ParserStatus, Parser}
import org.apache.commons.lang.StringUtils

/**
 * The Class VnExpress.net Plugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:57 PM
 *
 */

class VnExpressPlugin extends Parser with Logging {

  def defaultData = {
    val pluginData = new ParserPlugin
    pluginData.name = "VnExpress"
    pluginData.version = "0.0.1"
    pluginData.group = "vnexpress.net"
    pluginData.pluginClass = this.getClass.getName
    pluginData.enable = true
    pluginData.urlRegex = "*/vnexpress.net/*/*/2012/*"
    pluginData.titleSelection = ".content h1.Title"
    pluginData.descriptionSelection = ".content .Lead"
    pluginData.imgSelection = ".content td img"
    pluginData.urlTest = "http://vnexpress.net/"
    pluginData
  }

  override def beforeParse(link: Link, doc: Document, parserResult: ParserStatus): Boolean = {
    //Skip it, because the url is no longer exist
     if (doc.text().contains("Không tìm thấy đường dẫn này")) {
      parserResult.code = Parser.SKIP
      parserResult.info("The link is not exist " + link.url)
      return false
    }
    true
  }


  override def afterParse(link: Link, doc: Document, parserResult: ParserStatus) {
    //Remove another link inside vnexpress description
    if (link.description.contains(". >")) {
      link.description = link.description.split(". >")(0)
    }

    if (StringUtils.isBlank(link.featureImageUrl)) {
      val logo = doc.select(".img-logo")
      if (!logo.isEmpty) link.featureImageUrl = logo.attr("src")
    }

    super.afterParse(link, doc, parserResult)
  }
}
