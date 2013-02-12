package org.linkerz.crawl.topology.filter

import io.Source
import org.linkerz.core.matcher.SimpleRegexMatcher
import collection.mutable.ListBuffer

/**
 * The Class BlackUrlPattern.
 *
 * @author Nguyen Duc Dung
 * @since 2/12/13 11:28 AM
 *
 */
object BlackUrlPattern {

  lazy val regexps = {
    val strm = try {
      this.getClass.getClassLoader.getResourceAsStream("filter/black_url.lst")
    } catch {
      case _: Throwable => throw new IllegalArgumentException("Unavailable file black_url.lst")
    }
    val src = Source.fromInputStream(strm)

    val regexps = new ListBuffer[String]
    src.getLines().filter(!_.startsWith("#")).foreach(st => {
      regexps += st.trim
    })
    strm.close()

    regexps.toList
  }

  def matches(url: String) = regexps.exists(SimpleRegexMatcher.matcher(url, _))
}
