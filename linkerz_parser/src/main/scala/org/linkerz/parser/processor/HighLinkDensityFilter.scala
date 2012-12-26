package org.linkerz.parser.processor

import org.linkerz.parser.model.Article

/**
 * The Class RemoveHighLinkDensityElement.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 10:32 PM
 *
 */
class HighLinkDensityFilter extends Processor {
  def process(implicit article: Article) {
    article.textElements.foreach(element => {
      if (element.isHighLinkDensity) {
        element.isPotential = false
      }
    })
  }
}
