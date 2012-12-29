package org.linkerz.parser.util

import io.Source
import java.util.regex.Pattern

/**
 * The Class DirtyElementPattern.
 *
 * @author Nguyen Duc Dung
 * @since 12/29/12 5:54 PM
 *
 */
class DirtyElementPattern {

  lazy val regex = {
    val strm = try {
      this.getClass.getClassLoader.getResourceAsStream("filter/remove.lst")
    } catch {
      case _: Throwable => throw new IllegalArgumentException("Unavailable file remove.lst")
    }
    val src = Source.fromInputStream(strm)

    val sb = new StringBuilder
    src.getLines().filter(!_.startsWith("#")).foreach(st => {
      sb.append(st.trim + "|")
    })
    strm.close()
    val regex = sb.toString()

    //Remove last '|'
    regex.substring(0, regex.length - 1)
  }

  def pattern = Pattern.compile(regex)

  def matches(st: String) = pattern.matcher(st).matches()
}


