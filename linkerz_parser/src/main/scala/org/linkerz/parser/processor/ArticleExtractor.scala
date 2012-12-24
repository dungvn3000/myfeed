package org.linkerz.parser.processor

import collection.mutable.ListBuffer
import org.linkerz.parser.model._
import collection.JavaConversions._
import org.jsoup.nodes.Element
import org.linkerz.parser.util.ArticleUtil._
import org.linkerz.parser.model.LinkElement
import org.linkerz.parser.model.Article
import org.linkerz.parser.model.TextElement
import ElementWrapper._

/**
 * The Class ArticleExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 3:26 AM
 *
 */
class ArticleExtractor extends Processor {

  def process(article: Article) {
    implicit val articleElements = new ListBuffer[ArticleElement]
    val elements = article.doc.getAllElements

    elements.foreach(element => if (!element.shouldSkipParse) element.tagName match {
      case "title" => handleTitleElement(element)
      case "a" => handleAElement(element)
      case "img" => handleImgElement(element)
      case _ => handleElement(element)
    })

    article.elements = articleElements.toList
  }

  private def addToArticle(element: ArticleElement)(implicit articleElements: ListBuffer[ArticleElement]) {
    element.index = articleElements.size
    articleElements += element
  }

  private def handleAElement(element: Element)(implicit articleElements: ListBuffer[ArticleElement]) {
    val linkElement = LinkElement(element)
    addToArticle(linkElement)
  }

  private def handleImgElement(element: Element)(implicit articleElements: ListBuffer[ArticleElement]) {
    val imgElement = ImageElement(element)
    addToArticle(imgElement)
  }

  private def handleTitleElement(element: Element)(implicit articleElements: ListBuffer[ArticleElement]) {
    val titleElement = element.ownerDocument.select("title").first()
    if (titleElement != null) {
      addToArticle(TitleElement(titleElement))
    }
  }

  private def handleElement(element: Element)(implicit articleElements: ListBuffer[ArticleElement]) {
    if (isArticleContentTag(element.tag)) {
      if (element.detectTextBlock) {
        //In case this block has own text, to avoid duplicate.
        element.getInnerAllElements.foreach(_.shouldSkipParse = true)
      }

      val textElement = TextElement(element)
      if (textElement.hasText) {
        addToArticle(textElement)
      }
    }
  }

}
