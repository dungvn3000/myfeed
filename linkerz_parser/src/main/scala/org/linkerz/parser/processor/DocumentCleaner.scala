package org.linkerz.parser.processor

import org.linkerz.parser.model.Article

/**
 * The Class DocumentCleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/25/12 2:08 PM
 *
 */
class DocumentCleaner extends Processor {
  def process(implicit article: Article) {
    val doc = article.doc
    val cleanedHtml = doc.html.replaceAll("&nbsp;"," ")
    doc.html(cleanedHtml)
    doc.normalise()
  }
}
