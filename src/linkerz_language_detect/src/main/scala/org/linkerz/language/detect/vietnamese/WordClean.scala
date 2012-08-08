/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.language.detect.vietnamese

import org.springframework.util.StringUtils


/**
 * The Class WordClean.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 12:43 PM
 *
 */

object WordClean {

  def blackWord = Array("www.", "http:" , "https:" , ".net", ".com", ".org", ".info", ".vn")

  def clean(string: String) = {
    assert(string != null, "The string must be not null")

    //Step1: clean not mean word
    var cleanBlackWord = string
    blackWord.foreach(remove => cleanBlackWord = cleanBlackWord.replace(remove, ""))

    //Step2: Remove non-letter and non-number.
    val clean = new StringBuilder(cleanBlackWord)
    var index = 0
    while (clean.length > index) {
      if (!Character.isLetterOrDigit(clean(index)) && !isWhitespace(clean(index))) {
        clean.deleteCharAt(index)
      } else {
        index += 1
      }
    }
    cleanWhiteSpace(clean.toString())
  }

  def cleanWhiteSpace(string: String): String = {
    assert(string != null, "The string must be not null")
    if (string.length < 1) return string
    val clean = new StringBuilder(string)
    var last: Char = clean.charAt(0)
    var index = 0
    while (clean.length > index) {
      if (isWhitespace(last) && isWhitespace(clean.charAt(index))) {
        clean.deleteCharAt(index)
      } else {
        last = clean.charAt(index)
        index += 1
      }
    }
    StringUtils.trimWhitespace(clean.toString())
  }

  def isWhitespace(char: Char) = {
    Character.isWhitespace(char) ||
      Character.getType(char) == Character.SPACE_SEPARATOR
  }

}
