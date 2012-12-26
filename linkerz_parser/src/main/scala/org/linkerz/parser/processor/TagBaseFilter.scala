package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import org.apache.commons.lang.StringUtils

/**
 * The Class TagBaseFilter.
 *
 * @author Nguyen Duc Dung
 * @since 12/26/12 12:24 PM
 *
 */
class TagBaseFilter extends Processor {
  def process(implicit article: Article) {
    article.potentialElements.foreach(potentialElement => {
      val tagName = potentialElement.tag.getName
      val elementClass = potentialElement.className
      article.elements.foreach(el => {
        if (el.tag.getName == tagName
          && el.className == elementClass
          && (StringUtils.isNotBlank(elementClass) || el.parent == potentialElement.parent)
          && el.hasText) {
          el.isPotential = true
        }

        if (el.tag.getName == tagName
          && el.parent == potentialElement.parent
          && el.isSimilarClassName(potentialElement)) {
          el.isPotential = true
        }

      })
    })
  }
}
