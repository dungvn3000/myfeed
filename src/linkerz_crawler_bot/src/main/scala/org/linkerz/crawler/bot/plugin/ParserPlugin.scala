/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.mongodb.model.{ParseData, Link}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import com.googlecode.flaxcrawler.utils.UrlUtils
import grizzled.slf4j.Logging

/**
 * The Class ParserPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:52 PM
 *
 */

trait ParserPlugin extends Logging {

  /**
   * Check the url is suitable with the plugin or not
   * @param link
   * @return
   */
  def isMatch(link: Link): Boolean


  /**
   * Parse the link. The meta data will be add to the link after parsed it.
   * @param link
   * @return false if some thing go wrong.
   */
  def parse(link: Link): Boolean


  /**
   * Default parse method.
   * @param link
   * @param parseData
   */
  protected def parse(link: Link, parseData: ParseData) = {
    var someThingWrong = false

    val inputStream = new ByteArrayInputStream(link.content)
    val doc = Jsoup.parse(inputStream, link.contentEncoding, UrlUtils.getDomainName(link.url))

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

    !someThingWrong
  }
}
