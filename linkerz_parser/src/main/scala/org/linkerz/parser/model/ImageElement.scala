package org.linkerz.parser.model

import org.jsoup.nodes.Element

/**
 * The Class ImageBlock.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:04 AM
 *
 */
class ImageElement(_jsoupElement: Element)(implicit article: Article) extends ArticleElement(_jsoupElement) {

  def text = ""

  def src = jsoupElement.attr("src")

  /**
   * @return 100 when the element is potential.
   */
  override def score = if (isPotential) 100 else super.score

  override def toString = src
}
