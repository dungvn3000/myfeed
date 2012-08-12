/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.mongodb.model.{ParserPluginData, Link}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import com.googlecode.flaxcrawler.utils.UrlUtils
import grizzled.slf4j.Logging
import org.jsoup.nodes.Document
import org.linkerz.crawler.bot.matcher.SimpleRegexMatcher
import collection.mutable.ListBuffer
import org.linkerz.crawler.core.parser.{DefaultParser, ParserResult, Parser}
import org.linkerz.crawler.core.downloader.DownloadResult
import org.linkerz.crawler.core.model.WebPage

/**
 * The Class ParserPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:52 PM
 *
 */

trait ParserPlugin extends DefaultParser with Logging {

  var _pluginData: ParserPluginData = _

  /**
   * Check the url is suitable with the plugin or not
   * @param link
   * @return
   */
  def isMatch(link: Link): Boolean = {
    assert(link != null)
    SimpleRegexMatcher.matcher(link.url, pluginData.urlRegex)
  }

  /**
   * Parse the link. The meta data will be add to the link after parsed it.
   * @param link
   * @return false if some thing go wrong.
   */
  def parse(link: Link): ParserResult = {
    assert(link != null)
    parse(link, pluginData)
  }

  /**
   * Before parse a link.
   * @param link
   * @param doc
   * @return false then the parser will skip parse it.
   */
  def beforeParse(link: Link, doc: Document, parserResult: ParserResult): Boolean = true

  /**
   * After parse a link.
   * @param link
   * @param doc
   */
  def afterParse(link: Link, doc: Document, parserResult: ParserResult) {
    //Log error
    if (link.title == null || link.title.trim.isEmpty) {
      parserResult.error("Can not parse the title for " + link.url)
    }

    if (link.description == null || link.description.trim.isEmpty) {
      parserResult.error("Can not parse the description for " + link.url)
    }

    if (link.featureImageUrl == null || link.featureImageUrl.trim.isEmpty) {
      parserResult.error("Can not parse the image for " + link.url)
    }
  }


  override def parse(downloadResult: DownloadResult) = {
    super.parse(downloadResult)
  }

  /**
   * Default parse method.
   * @param link
   * @param parseData
   */
  protected def parse(link: Link, parseData: ParserPluginData): ParserResult = {
    val parserResult = new ParserResult(new WebPage)

    val inputStream = new ByteArrayInputStream(link.content)
    val doc = Jsoup.parse(inputStream, link.contentEncoding, UrlUtils.getDomainName(link.url))

    if (!beforeParse(link, doc, parserResult)) {
      return parserResult
    }

    val title = doc.select(parseData.titleSelection)
    val description = doc.select(parseData.descriptionSelection)
    val img = doc.select(parseData.imgSelection)

    var titleText = title.text()
    if (parseData.titleAttName != null
      && parseData.titleAttName.trim.length > 0) {
      titleText = title.attr(parseData.titleAttName)
    }

    if (titleText.length > parseData.titleMaxLength
      && parseData.titleMaxLength > 0) {
      titleText = titleText.substring(0, parseData.titleMaxLength)
    }

    //To make sure the title will be never null
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = doc.title()
    }

    //Recheck again
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = link.url
    }

    var descriptionText = description.text()
    if (parseData.descriptionAttName != null
      && parseData.descriptionAttName.trim.length > 0) {
      descriptionText = description.attr(parseData.descriptionAttName)
    }

    if (descriptionText.length > parseData.descriptionMaxLength
      && parseData.descriptionMaxLength > 0) {
      descriptionText = descriptionText.substring(0, parseData.descriptionMaxLength)
    }

    if (descriptionText == null || descriptionText.trim.isEmpty) {
      descriptionText = titleText
    }

    link.title = titleText
    link.description = descriptionText
    link.featureImageUrl = img.attr("src")

    afterParse(link, doc, parserResult)

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