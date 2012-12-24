package org.linkerz.parser.model

import org.jsoup.nodes.Document


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

  def text = {
    val sb = new StringBuilder
    textElements.foreach(element => sb.append(element.text))
    sb.toString()
  }

  /**
   * This is using for debugging.
   * @return
   */
  def prettyText = {
    val sb = new StringBuilder
    textElements.foreach(element => {
      sb.append(element.text)
      sb.append("\n")
    })
    sb.toString()
  }

  def title = titleElement.getOrElse("")

  def images = imageElements

  var elements: List[ArticleElement] = Nil

}
