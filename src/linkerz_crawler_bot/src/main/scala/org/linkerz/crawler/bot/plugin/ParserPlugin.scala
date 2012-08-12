/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.mongodb.model.ParserPluginData
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import grizzled.slf4j.Logging
import org.jsoup.nodes.Document
import org.linkerz.crawler.bot.matcher.SimpleRegexMatcher
import org.linkerz.crawler.core.parser.{DefaultParser, ParserResult}
import org.linkerz.crawler.core.downloader.DownloadResult
import org.linkerz.crawler.core.model.WebPage
import org.apache.commons.lang.StringUtils

/**
 * The Class ParserPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:52 PM
 *
 */

trait ParserPlugin extends DefaultParser with Logging {

  var _pluginData: ParserPluginData = _

  def isMatch(url: String): Boolean = {
    assert(url != null)
    SimpleRegexMatcher.matcher(url, pluginData.urlRegex)
  }

  def beforeParse(webPage: WebPage, doc: Document, parserResult: ParserResult): Boolean = true

  def afterParse(webPage: WebPage, doc: Document, parserResult: ParserResult) {
    //Log error
    if (StringUtils.isBlank(webPage.title)) {
      parserResult.error("Can not parse the title for " + webPage.webUrl.url)
    }

    if (StringUtils.isBlank(webPage.description)) {
      parserResult.error("Can not parse the description for " + webPage.webUrl.url)
    }

    if (StringUtils.isBlank(webPage.featureImageUrl)) {
      parserResult.error("Can not parse the image for " + webPage.webUrl.url)
    }
  }


  override def parse(downloadResult: DownloadResult): ParserResult = {
    val parserResult = super.parse(downloadResult)
    val webPage = parserResult.webPage
    val inputStream = new ByteArrayInputStream(webPage.content)
    val doc = Jsoup.parse(inputStream, webPage.contentEncoding, webPage.webUrl.domainName)

    if (!beforeParse(webPage, doc, parserResult)) {
      return parserResult
    }

    val title = doc.select(pluginData.titleSelection)
    val description = doc.select(pluginData.descriptionSelection)
    val img = doc.select(pluginData.imgSelection)

    var titleText = title.text()
    if (pluginData.titleAttName != null
      && pluginData.titleAttName.trim.length > 0) {
      titleText = title.attr(pluginData.titleAttName)
    }

    if (titleText.length > pluginData.titleMaxLength
      && pluginData.titleMaxLength > 0) {
      titleText = titleText.substring(0, pluginData.titleMaxLength)
    }

    //To make sure the title will be never null
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = doc.title()
    }

    //Recheck again
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = webPage.webUrl.url
    }

    var descriptionText = description.text()
    if (pluginData.descriptionAttName != null
      && pluginData.descriptionAttName.trim.length > 0) {
      descriptionText = description.attr(pluginData.descriptionAttName)
    }

    if (descriptionText.length > pluginData.descriptionMaxLength
      && pluginData.descriptionMaxLength > 0) {
      descriptionText = descriptionText.substring(0, pluginData.descriptionMaxLength)
    }

    if (descriptionText == null || descriptionText.trim.isEmpty) {
      descriptionText = titleText
    }

    webPage.title = titleText
    webPage.description = descriptionText
    webPage.featureImageUrl = img.attr("src")

    afterParse(webPage, doc, parserResult)

    parserResult

  }

  /**
   * Data for the plugin
   * @return
   */
  def pluginData: ParserPluginData = {
    if (_pluginData == null) {
      return defaultData
    }
    _pluginData
  }

  /**
   * Setter for the data
   * @param pluginData
   */
  def pluginData_=(pluginData: ParserPluginData) {
    _pluginData = pluginData
  }

  /**
   * Default data.
   * @return
   */
  def defaultData: ParserPluginData
}