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
import org.jsoup.nodes.Document
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class LinkerZParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 1:40 AM
 *
 */

class LinkerZParser extends Logging {

  def parse(url: String, linkParseData: LinkParseData): LinkerZParserResult = {
    assert(url != null)
    assert(linkParseData != null)
    if (SimpleRegexMatcher.matcher(url, linkParseData.urlRegex)) {
      val fetcher = new Fetcher
      val result = fetcher.fetch(new WebUrl(url))
      return parse(result.webPage.asLink(), linkParseData)
    } else {
      info("Url is not match: " + url + " - " + linkParseData.urlRegex)
    }
    return null
  }

  def parse(link: Link, linkParseData: LinkParseData): LinkerZParserResult = {
    assert(link != null)
    assert(linkParseData != null)
    if (SimpleRegexMatcher.matcher(link.url, linkParseData.urlRegex)) {
      val inputStream = new ByteArrayInputStream(link.content)
      val doc = Jsoup.parse(inputStream, link.contentEncoding, UrlUtils.getDomainName(link.url))
      return parse(doc, linkParseData)
    } else {
      info("Url is not match: " + link.url + " - " + linkParseData.urlRegex)
    }
    return null
  }

  private def parse(doc: Document, linkParseData: LinkParseData): LinkerZParserResult = {
    val title = doc.select(linkParseData.titleSelection)
    val description = doc.select(linkParseData.descriptionSelection)
    val img = doc.select(linkParseData.imgSelection)

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

    new LinkerZParserResult(titleText, descriptionText, img.attr("src"))
  }
}

case class LinkerZParserResult(title: String, description: String, imgSrc: String)
