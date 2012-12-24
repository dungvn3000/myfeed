package org.linkerz.parser.processor

import org.linkerz.parser.model.Article

/**
 * The Class RemoveHighLinkDensityElement.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 10:32 PM
 *
 */
class RemoveHighLinkDensityElement extends Processor {
  def process(article: Article) {
    val elements = article.elements.toBuffer
    article.textElements.foreach(element => {
      if (element.isHighLinkDensity) {
        elements -= element
      }
    })

    article.elements = elements.toList
  }
}
