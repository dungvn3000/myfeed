package org.linkerz.parser.model

import org.jsoup.nodes.Element

/**
 * This model using for Link Parser.
 * This class might be confuse with LinkElement class.
 * The different is the LinkElement is child of Article model, and it was parsed by ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 1/27/13 4:03 AM
 *
 */
case class Link(url: String, element: Element) {

  /**
   * Maximum is 1 and minimum is 0.
   */
  var score: Double = 0d

  def isContainImage = !element.select("img").isEmpty

  override def equals(obj: Any) = obj.isInstanceOf[Link] && obj.asInstanceOf[Link].url == url

  override def hashCode() = url.hashCode
}
