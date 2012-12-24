package org.linkerz.parser.processor

import org.linkerz.parser.model.Article

/**
 * The Class NumbOfWordFilter.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 5:14 AM
 *
 */
class NumbOfWordFilter(minNumbOfStopWord: Int = 2, minNumbOfWord: Int = 10) extends Processor {
  def process(article: Article) {
    article.textElements.foreach(el => {
      if (el.stopWordCount >= minNumbOfStopWord && el.wordCount >= minNumbOfWord) {
        el.isPotential = true
      }
    })
  }
}
