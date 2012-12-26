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
    val elements = article.doc.getAllElements

    elements.foreach(element => if (!element.isSkipParse) element.tagName match {
      case "title" => //ignore
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
    val imageElements = element.select("img")
    //If a link content an image then treat it like an image
    if (imageElements.isEmpty) {
      val linkElement = LinkElement(element)
      addToArticle(linkElement)
    }
  }

  private def handleImgElement(element: Element)(implicit articleElements: ListBuffer[ArticleElement], article: Article) {
    val imgElement = ImageElement(element)
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
        val textElement = TextElement(element)
        if (textElement.hasText) {
          addToArticle(textElement)
        }
      }

    }
  }

}