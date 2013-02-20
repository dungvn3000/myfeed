package org.linkerz.parser.model

import org.jsoup.nodes.{Element, Document}
import collection.JavaConverters._

/**
 * The Class Article.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:00 AM
 *
 */
class Article(val doc: Document, private val _containerElement: Option[Element] = None) {

  /**
   * Default is vi.
   */
  var languageCode = "vi"

  var title = ""

  //This element will contain all text content element. Default is body element.
  var containerElement: Element = _containerElement.getOrElse(doc.body())

  def textElements: List[TextElement] = elements.filter(_.isInstanceOf[TextElement]).map(_.asInstanceOf[TextElement])

  def imageElements: List[ImageElement] = elements.filter(_.isInstanceOf[ImageElement]).map(_.asInstanceOf[ImageElement])

  def linkElements: List[LinkElement] = elements.filter(_.isInstanceOf[LinkElement]).map(_.asInstanceOf[LinkElement])

  def potentialElements: List[ArticleElement] = elements.filter(_.isPotential)

  def contentElements: List[ArticleElement] = elements.filter(_.isContent)

  def textContentElements: List[ArticleElement] = textElements.filter(_.isContent)

  def jsoupElements = elements.map(_.jsoupElement)

  def text = {
    val sb = new StringBuilder
    textContentElements.foreach(element => {
      sb.append(element.text)
      sb.append("\n")
    })
    sb.toString()
  }

  /**
   * The short description text for the article. Find the longest block and split it to a description.
   * @return
   */
  def description(maxNumbOfWord: Int = 30) = {
    var bestDescription: String = ""
    if (!textContentElements.isEmpty) {
      bestDescription = textContentElements.sortBy(-_.text.length).head.text
      val words = bestDescription.split(' ')
      val sb = new StringBuilder
      var wordCount = 0
      words.foreach(word => {
        if (wordCount < maxNumbOfWord) {
          sb.append(word)
          sb.append(" ")
          wordCount +=1
        }
      })
      bestDescription = sb.toString()
    }
    bestDescription
  }

  /**
   * This is using for debugging.
   * @return
   */
  def prettyText(wordInLine: Int = 20) = {
    val sb = new StringBuilder
    textContentElements.foreach(element => {
      var count = 0
      element.text.split(" ").foreach(word => {
        sb.append(word + " ")
        if (count > wordInLine) {
          sb.append("\n")
          count = 0
        }
        count += 1
      })
      sb.append("\n")
    })
    sb.toString()
  }

  def images = imageElements.filter(_.isContent)

  //For java api
  def imagesAsJavaList = imageElements.filter(_.isContent).asJava

  /**
   * Article element list without title element.
   */
  var elements: List[ArticleElement] = Nil

  override def toString = text
}
