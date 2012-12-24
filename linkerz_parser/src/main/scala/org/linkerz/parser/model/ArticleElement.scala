package org.linkerz.parser.model

/**
 * The Class ArticleElement.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 3:24 AM
 *
 */
trait ArticleElement {

  /**
   * Index of this element inside an article.
   */
  var index = 0

  var isPotential = false

  def text: String

}
