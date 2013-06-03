/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.core.matcher

import org.apache.commons.digester.{SimpleRegexMatcher => SMatcher}

/**
 * The Class SimpleRegexMatcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 2:11 AM
 *
 */

object SimpleRegexMatcher {

  val simpleMatcher = new SMatcher

  def matcher(string: String, patten: String): Boolean = {
    simpleMatcher.`match`(string, patten)
  }

}
