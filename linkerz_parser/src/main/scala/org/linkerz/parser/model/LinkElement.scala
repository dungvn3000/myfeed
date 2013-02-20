package org.linkerz.parser.model

import org.jsoup.nodes.Element

/**
 * The Class LinkElement.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:06 AM
 *
 */
class LinkElement(_jsoupElement: Element)(implicit article: Article) extends ArticleElement(_jsoupElement) {

  def text = jsoupElement.text()

  /**
   * Link element should be minus score
   * @return
   */
  override def score = -200

  override def toString = jsoupElement.attr("href")
}
