package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import org.apache.commons.lang.StringUtils
import org.linkerz.core.string.RichString._

/**
 * This class will base on the title to find potential element.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 8:13 AM
 *
 */
class TitleBaseFilter extends Processor {
  def process(implicit article: Article) {
    if (StringUtils.isNotBlank(article.title)) {
      article.elements.foreach(element => if (element.text.toLowerCase.trimToEmpty == article.title.toLowerCase.trimToEmpty) {
        element.isPotential = true
        element.isTitle = true
      })
    }
  }
}
