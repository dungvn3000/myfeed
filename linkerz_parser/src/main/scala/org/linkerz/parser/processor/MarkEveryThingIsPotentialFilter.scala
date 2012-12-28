package org.linkerz.parser.processor

import org.linkerz.parser.model.Article

/**
 * The Class MarkEveryThingIsPotentialFilter.
 *
 * @author Nguyen Duc Dung
 * @since 12/28/12 5:30 PM
 *
 */
class MarkEveryThingIsPotentialFilter extends Processor {
  def process(implicit article: Article) {
    article.elements.foreach(_.isPotential = true)
  }
}
