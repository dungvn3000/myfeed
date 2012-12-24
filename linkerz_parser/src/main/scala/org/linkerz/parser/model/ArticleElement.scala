package org.linkerz.parser.model

import org.jsoup.nodes.Element
import org.apache.commons.lang.StringUtils

/**
 * The Class ArticleElement.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 3:24 AM
 *
 */
abstract class ArticleElement(_jsoupElement: Element) {

  /**
   * Index of this element inside an article.
   */
  var index = 0

  var isPotential = false

  def text: String

  def hasText = StringUtils.isNotBlank(text)

  def jsoupElement = _jsoupElement

}
