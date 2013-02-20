package org.linkerz.parser.processor

import collection.mutable.ListBuffer
import org.linkerz.parser.model._
import collection.JavaConversions._
import org.jsoup.nodes.Element
import org.linkerz.parser.util.ArticleUtil._
import org.linkerz.parser.model.LinkElement
import org.linkerz.parser.model.Article
import org.linkerz.parser.model.TextElement
import JsoupElementWrapper._

/**
 * The Class ArticleExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 3:26 AM
 *
 */
class ArticleExtractor extends Processor {

  def process(implicit article: Article) {
    implicit val articleElements = new ListBuffer[ArticleElement]
    val elements = article.containerElement.getAllElements

    elements.foreach(element => if (!element.isSkipParse) element.tagName match {
      case "title" => //Ignore
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

  private def handleAElement(element: Element)(implicit articleElements: ListBuffer[ArticleElement], article: Article) {
    //A link element should be empty
    if (element.children.isEmpty) {
      val linkElement = new LinkElement(element)
      addToArticle(linkElement)
    } else {
      //If not
      val imageElements = element.select("img")
      if (imageElements.isEmpty) {
        val linkElement = new LinkElement(element)
        addToArticle(linkElement)

        //Skip parse a element content except img element
        element.innerAllElements.foreach(_.isSkipParse = true)
      }
    }
  }

  private def handleImgElement(element: Element)(implicit articleElements: ListBuffer[ArticleElement], article: Article) {
    val imgElement = new ImageElement(element)
    addToArticle(imgElement)
  }

  private def handleElement(element: Element)(implicit articleElements: ListBuffer[ArticleElement], article: Article) {
    if (isArticleContentTag(element.tag)) {

      if (element.isHidden) {
        //If it is hidden skip parse all children.
        element.getAllElements.foreach(_.isSkipParse = true)
      } else if (element.detectTextBlock) {
        //In case this block has own text, to avoid duplicate.
        element.innerAllElements.foreach(inner => if (inner.tagName != "img") inner.isSkipParse = true)
      }

      if (!element.isHidden) {
        val textElement = new TextElement(element)
        if (textElement.hasText) {
          addToArticle(textElement)
        }
      }

    }
  }

}