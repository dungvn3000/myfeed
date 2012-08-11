/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.mongodb.model.{ParserPlugin, Link}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import com.googlecode.flaxcrawler.utils.UrlUtils
import grizzled.slf4j.Logging
import org.jsoup.nodes.Document
import org.linkerz.crawler.bot.matcher.SimpleRegexMatcher

/**
 * The Class ParserPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:52 PM
 *
 */

trait Parser extends Logging {

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
  def parse(link: Link): Boolean = {
    assert(link != null)
    parse(link, pluginData)
  }

  /**
   * Before parse a link.
   * @param link
   * @param doc
   * @return false then the parser will skip parse it.
   */
  def beforeParse(link: Link, doc: Document): Boolean = true

  /**
   * After parse a link.
   * @param link
   * @param doc
   */
  def afterParse(link: Link, doc: Document) {}

  /**
   * Default parse method.
   * @param link
   * @param parseData
   */
  protected def parse(link: Link, parseData: ParserPlugin): Boolean = {
    var someThingWrong = false

    val inputStream = new ByteArrayInputStream(link.content)
    val doc = Jsoup.parse(inputStream, link.contentEncoding, UrlUtils.getDomainName(link.url))

    if (!beforeParse(link, doc)) return false

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

    val imgSrc = img.attr("src")

    //Log error
    if (titleText == null || titleText.trim.isEmpty) {
      info("Can not parse the title for " + link.url)
      someThingWrong = true
    }

    if (descriptionText == null || descriptionText.trim.isEmpty) {
      info("Can not parse the description for " + link.url)
      someThingWrong = true
    }

    if (imgSrc == null || imgSrc.trim.isEmpty) {
      info("Can not parse the image for " + link.url)
      someThingWrong = true
    }

    link.title = titleText
    link.description = descriptionText
    link.featureImageUrl = img.attr("src")

    afterParse(link, doc)

    !someThingWrong
  }

  /**
   * Data for the plugin
   * @return
   */
  def pluginData: ParserPlugin

  /**
   * Setter for the data
   * @param pluginData
   */
  def pluginData_=(pluginData: ParserPlugin) {}

}
