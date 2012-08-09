/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.parser

import org.linkerz.mongodb.model.{LinkParseData, Link}
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import com.googlecode.flaxcrawler.utils.UrlUtils
import org.linkerz.crawler.bot.matcher.SimpleRegexMatcher
import grizzled.slf4j.Logging

/**
 * The Class LinkerZParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 1:40 AM
 *
 */

class LinkerZParser extends Logging {

  def parse(link: Link, linkParseData: LinkParseData) {
    assert(link != null)
    assert(linkParseData != null)
    if (SimpleRegexMatcher.matcher(link.url, linkParseData.urlRegex)) {
      val inputStream = new ByteArrayInputStream(link.content)
      val doc = Jsoup.parse(inputStream, link.contentEncoding, UrlUtils.getDomainName(link.url))

      val title = doc.select(linkParseData.titleSelection)
      val description = doc.select(linkParseData.descriptionSelection)
      val content = doc.select(linkParseData.contentSelection)
      val img = doc.select(linkParseData.imgSelection)

      info(link.url)
      info(title.toString)
      info(description.toString)
      info(content.toString)
      info(img.toString)

      var titleText = title.text()
      if (linkParseData.titleAttName != null
        && linkParseData.titleAttName.trim.length > 0) {
        titleText = title.attr(linkParseData.titleAttName)
      }

      if (titleText.length > linkParseData.titleMaxLength
        && linkParseData.titleMaxLength > 0) {
        titleText = titleText.substring(0, linkParseData.titleMaxLength)
      }

      var descriptionText = description.text()
      if (linkParseData.descriptionAttName != null
        && linkParseData.descriptionAttName.trim.length > 0) {
        descriptionText = description.attr(linkParseData.descriptionAttName)
      }

      if (descriptionText.length > linkParseData.descriptionMaxLength
        && linkParseData.descriptionMaxLength > 0) {
        descriptionText = descriptionText.substring(0, linkParseData.descriptionMaxLength)
      }

      var contentText = description.text()
      if (linkParseData.contentAttName != null
        && linkParseData.contentAttName.trim.length > 0) {
        contentText = description.attr(linkParseData.contentAttName)
      }

      if (contentText.length > linkParseData.contentMaxLength
        && linkParseData.contentMaxLength > 0) {
        contentText = contentText.substring(0, linkParseData.contentMaxLength)
      }

      println(titleText)
      println(descriptionText)
      println(contentText)
      println(img.attr("src"))
    } else {
      info("Url is not match: " + link.url + " - " + linkParseData.urlRegex)
    }
  }

}
