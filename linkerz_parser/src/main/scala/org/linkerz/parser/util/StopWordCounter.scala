package org.linkerz.parser.util

import breeze.text.tokenize.JavaWordTokenizer
import io.Source
import org.apache.commons.lang.StringUtils

/**
 * The Class StopWordCounter.
 *
 * @author Nguyen Duc Dung
 * @since 12/19/12, 6:10 PM
 *
 */
class StopWordCounter(language: String) {

  val tokenizer = JavaWordTokenizer

  val stopWords = {
    val strm = try {
      this.getClass.getClassLoader.getResourceAsStream("stopwords/" + language.toLowerCase + ".lst")
    } catch {
      case _: Throwable => throw new IllegalArgumentException("Unavailable language: " + language)
    }
    val src = Source.fromInputStream(strm)

    val ret = Set() ++ src.getLines().filter(!_.startsWith("#")).map(_.trim)
    strm.close()
    ret
  }

  def count(st: String) = {
    var count = 0
    if (StringUtils.isNotBlank(st)) {
      tokenizer(st).foreach(word => if (stopWords.contains(word)) count += 1)
    }
    count
  }

}
