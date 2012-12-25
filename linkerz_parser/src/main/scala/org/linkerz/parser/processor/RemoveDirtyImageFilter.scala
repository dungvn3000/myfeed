package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import org.linkerz.parser.util.DirtyImagePattern

/**
 * The Class ImageFilter.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 9:15 PM
 *
 */
class RemoveDirtyImageFilter extends Processor {

  def process(implicit article: Article) {
    val dirtyImagePattern = new DirtyImagePattern(article.languageCode)
    val elements = article.elements.toBuffer
    article.imageElements.foreach(element => {
      if (dirtyImagePattern.matches(element.src)) {
        elements -= element
      }
    })

    article.elements = elements.toList
  }
}
