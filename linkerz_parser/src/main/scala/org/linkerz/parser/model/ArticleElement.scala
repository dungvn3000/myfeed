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
abstract case class ArticleElement(jsoupElement: Element)(implicit article: Article) {

  /**
   * Index of this element inside an article.
   */
  var index = 0

  /**
   * This attribute mean this element might is a part of content.
   */
  var isPotential = false

  var isContent = false

  var isTitle = false

  def tag = jsoupElement.tag()

  def tagName = jsoupElement.tagName()

  def className = jsoupElement.className()

  /**
   * Check similarity class name between two elements
   * @param otherElement
   * @return if two element has more than one same class name.
   */
  def isSimilarClassName(otherElement: ArticleElement) = {
    var similar = false

    val otherClassNames = otherElement.className.split(" ")
    val classNames = className.split(" ")

    otherClassNames.foreach(otherName => {
      classNames.foreach(name => {
        if (StringUtils.equalsIgnoreCase(otherName.trim, name.trim)
          && StringUtils.isNotBlank(otherName)) {
          similar = true
        }
      })
    })

    similar
  }

  def parent = jsoupElement.parent()

  def text: String

  def hasText = StringUtils.isNotBlank(text)

  /**
   * Score to evaluate this element.
   * @return
   */
  def score: Double = 0

}
