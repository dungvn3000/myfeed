package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import org.linkerz.core.string.RichString._

/**
 * This class will base on the description to find potential element.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 8:13 AM
 *
 */
class DescriptionBaseFilter extends Processor {
  def process(implicit article: Article) {
    if (article.description.isNotBlank) {
      article.elements.foreach(element => if (element.text.toLowerCase.trimToEmpty == article.description.toLowerCase.trimToEmpty) {
        element.isPotential = true
        element.isContent = true
      })
    }
  }
}
