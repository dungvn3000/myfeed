/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.matcher

import org.apache.commons.digester.SimpleRegexMatcher

/**
 * The Class SimpleRegexMatcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 2:11 AM
 *
 */

object SimpleRegexMatcher extends SimpleRegexMatcher {

  val simpleMatcher = new SimpleRegexMatcher

  def matcher(string: String, patten: String): Boolean = {
    simpleMatcher.`match`(string, patten)
  }

}
