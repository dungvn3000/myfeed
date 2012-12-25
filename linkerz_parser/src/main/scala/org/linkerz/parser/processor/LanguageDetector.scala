package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import org.linkerz.parser.util.StopWordCounter

/**
 * The Class LanguageDetector.
 *
 * @author Nguyen Duc Dung
 * @since 12/25/12 1:22 PM
 *
 */
class LanguageDetector extends Processor {
  val viCounter = new StopWordCounter("vi")
  val enCounter = new StopWordCounter("en")
  def process(implicit article: Article) {
    val viNumbOfStopWord = viCounter.count(article.doc.text())
    val enNumbOfStopWord = enCounter.count(article.doc.text())

    if (viNumbOfStopWord > enNumbOfStopWord) {
      article.languageCode = "vi"
    } else {
      article.languageCode = "en"
    }
  }
}
