package org.linkerz.crawl.topology.parser.core

/**
 * This class import form https://github.com/jiminoc/goose
 *
 * Created by IntelliJ IDEA.
 * User: robbie
 * Date: 5/13/11
 * Time: 3:53 PM
 */

import java.util.regex.Pattern
import com.gravity.goose.text.string

class StringSplitter {
  def this(pattern: String) {
    this()
    this.pattern = Pattern.compile(pattern)
  }

  def split(input: String): Array[String] = {
    if (string.isNullOrEmpty(input)) return string.emptyArray
    pattern.split(input)
  }

  private var pattern: Pattern = null
}
