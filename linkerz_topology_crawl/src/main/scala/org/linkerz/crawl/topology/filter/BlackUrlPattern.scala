package org.linkerz.crawl.topology.filter

import io.Source
import org.linkerz.core.matcher.SimpleRegexMatcher
import collection.mutable.ListBuffer
import org.linkerz.model.BlackUrl

/**
 * The Class BlackUrlPattern.
 *
 * @author Nguyen Duc Dung
 * @since 2/12/13 11:28 AM
 *
 */
class BlackUrlPattern(blackUrls: List[BlackUrl] = Nil) {

  val regexps = {
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

    blackUrls.foreach(blackUrl => {
      regexps += blackUrl.urlRegex
    })

    regexps.toList
  }

  def matches(url: String) = regexps.exists(SimpleRegexMatcher.matcher(url, _))
}
