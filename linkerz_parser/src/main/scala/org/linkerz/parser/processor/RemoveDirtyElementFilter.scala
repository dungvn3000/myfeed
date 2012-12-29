package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import org.linkerz.parser.util.DirtyElementPattern
import collection.JavaConversions._

/**
 * The Class RemoveDirtyElementFilter. This filter should only using for manual mode,
 * because i might take bad effect result of auto mode.
 *
 * @author Nguyen Duc Dung
 * @since 12/29/12 5:53 PM
 *
 */
class RemoveDirtyElementFilter extends Processor {

  val dirtyPattern = new DirtyElementPattern

  def process(implicit article: Article) {
    val removeElements = article.containerElement.getAllElements.filter(element => {
      dirtyPattern.matches(element.className) || dirtyPattern.matches(element.id)
    })

    removeElements.foreach(_.remove())
  }
}
