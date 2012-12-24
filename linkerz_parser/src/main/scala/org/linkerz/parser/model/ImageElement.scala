package org.linkerz.parser.model

import org.jsoup.nodes.Element

/**
 * The Class ImageBlock.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:04 AM
 *
 */
case class ImageElement(_jsoupElement: Element) extends ArticleElement(_jsoupElement) {
  def text = ""

  def src = _jsoupElement.attr("src")
}
