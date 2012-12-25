package org.linkerz.parser.util

import io.Source
import java.util.regex.Pattern

/**
 * The Class DirtyImagePattern.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 9:50 PM
 *
 */
class DirtyImagePattern {

  lazy val regex = {
    val strm = try {
      this.getClass.getClassLoader.getResourceAsStream("image/dirty_image.lst")
    } catch {
      case _: Throwable => throw new IllegalArgumentException("Unavailable file dirty_image.lst")
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
