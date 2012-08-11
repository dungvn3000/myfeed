/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import grizzled.slf4j.Logging
import org.linkerz.mongodb.model.{ParseData, Link}
import org.linkerz.crawler.bot.matcher.SimpleRegexMatcher

/**
 * The Class VnExpress.net Plugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:57 PM
 *
 */

class VnExpressPlugin extends ParserPlugin with Logging {

  /**
   * Load from database
   */
  var parseData = new ParseData

  def isMatch(link: Link) = {
    assert(link != null)
    SimpleRegexMatcher.matcher(link.url, parseData.urlRegex)
  }

  def parse(link: Link) = {
    assert(link != null)
    val result = parse(link, parseData)

    //Remove another link inside vnexpress description
    if (link.description.contains(". >")) {
      link.description = link.description.split(". >")(0)
    }

    result
  }
}
