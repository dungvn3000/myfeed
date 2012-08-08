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

  val notWord = Array(":", "/", "'", ".", ",", "(", ")", "|",
    "&", "-", "\"", "“", "©", "®", "+", "*", "/", "-", "=", "{", "}",
    "%", "$", "#", "!", ";", "»", "¥", "»¹", "»•", "»±")

  def clean(string: String) = {
    assert(string != null, "The string must be not null")
    var clean = string
    notWord.foreach(remove => clean = clean.replace(remove, ""))
    cleanWhiteSpace(clean)
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
