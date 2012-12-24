package org.linkerz.parser.model

import org.jsoup.nodes.{Element, Document}


/**
 * The Class Article.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:00 AM
 *
 */
case class Article(doc: Document) {

  def titleElement: Option[TitleElement] = elements.find(_.isInstanceOf[TitleElement]).map(_.asInstanceOf[TitleElement])

  def textElements: List[TextElement] = elements.filter(_.isInstanceOf[TextElement]).map(_.asInstanceOf[TextElement])

  def imageElements: List[ImageElement] = elements.filter(_.isInstanceOf[ImageElement]).map(_.asInstanceOf[ImageElement])

  def linkElements: List[LinkElement] = elements.filter(_.isInstanceOf[LinkElement]).map(_.asInstanceOf[LinkElement])

  def potentialElements: List[ArticleElement] = elements.filter(_.isPotential)

  def jsoupElements = elements.map(_.jsoupElement)

  var elements: List[ArticleElement] = Nil

  var contentElement: Option[Element] = None
}
