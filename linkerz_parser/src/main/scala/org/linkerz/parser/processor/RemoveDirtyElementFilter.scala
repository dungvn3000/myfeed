package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import org.linkerz.parser.util.DirtyElementPattern

/**
 * The Class RemoveDirtyElementFilter.
 *
 * @author Nguyen Duc Dung
 * @since 12/29/12 5:53 PM
 *
 */
class RemoveDirtyElementFilter extends Processor {

  val dirtyPattern = new DirtyElementPattern

  def process(implicit article: Article) {
    article.potentialElements.foreach(element => {
      if (dirtyPattern.matches(element.className) || dirtyPattern.matches(element.id)) {
        element.isPotential = false
      }
    })
  }
}
