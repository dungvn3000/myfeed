package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import collection.JavaConversions._

/**
 * This class will find the lowest element contain all content element.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 7:27 PM
 *
 */
class ContainerElementDetector extends Processor {
  def process(implicit article: Article) {
    val elements = article.doc.getAllElements
    val textElements = article.textContentElements.map(_.jsoupElement)
    var containerElement = article.doc.body()

    elements.foreach(el => {
      if (el.getAllElements.containsAll(textElements)) containerElement = el
    })

    article.containerElement = containerElement
  }
}
