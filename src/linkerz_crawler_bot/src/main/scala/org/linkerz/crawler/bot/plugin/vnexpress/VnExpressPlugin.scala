/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin.vnexpress

import grizzled.slf4j.Logging
import org.linkerz.mongodb.model.{ParserPlugin, Link}
import org.jsoup.nodes.Document
import org.linkerz.crawler.bot.plugin.Parser

/**
 * The Class VnExpress.net Plugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:57 PM
 *
 */

class VnExpressPlugin extends Parser with Logging {

  var _pluginData: ParserPlugin = _

  override def pluginData_=(pluginData: ParserPlugin) {
    _pluginData = pluginData
  }

  def pluginData = {
    if (_pluginData == null) {
      /**
       * Default data
       */
      _pluginData = new ParserPlugin
      _pluginData.pluginName = "VnExpress"
      _pluginData.pluginVersion = "0.0.1"
      _pluginData.pluginGroup = "vnexpress.net"
      _pluginData.pluginClass = this.getClass.getName
      _pluginData.enable = true
      _pluginData.urlRegex = "*/vnexpress.net/*/*/2012/*"
      _pluginData.titleSelection = ".content h1.Title"
      _pluginData.descriptionSelection = ".content .Lead"
      _pluginData.imgSelection = ".content img"
      _pluginData.urlTest = "http://vnexpress.net/"
    }
    _pluginData
  }


  override def beforeParse(link: Link, doc: Document): Boolean = {
    //Skip it, because the url is no longer exist
    if (doc.text().contains("Không tìm thấy đường dẫn này")) {
      return false
    }
    true
  }

  override def afterParse(link: Link, doc: Document) {
    //Remove another link inside vnexpress description
    if (link.description.contains(". >")) {
      link.description = link.description.split(". >")(0)
    }
  }
}
