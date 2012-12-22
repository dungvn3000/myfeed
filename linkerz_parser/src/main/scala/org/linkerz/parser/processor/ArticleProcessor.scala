package org.linkerz.parser.processor

import collection.mutable.ListBuffer
import org.linkerz.parser.model._
import collection.JavaConversions._
import org.jsoup.nodes.Element
import org.apache.commons.lang.StringUtils
import org.linkerz.parser.util.ArticleUtil._
import org.linkerz.parser.model.LinkElement
import scala.Some
import org.linkerz.parser.model.Article
import org.linkerz.parser.model.TextElement

/**
 * The Class ArticleExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 3:26 AM
 *
 */
object ArticleProcessor extends Processor {

  def process(article: Article) {
    val articleElements = new ListBuffer[ArticleElement]
    val elements = article.doc.getAllElements

    elements.foreach(element => element.tagName match {
      case "title" => handleTitleElement(element).map(articleElements += _)
      case "a" => handleAElement(element).map(articleElements += _)
      case "img" => handleImgElement(element).map(articleElements += _)
      case _ => handleElement(element).map(articleElements += _)
    })

    article.elements = articleElements.toList
  }

  private def handleAElement(element: Element) = Some(LinkElement(element))

  private def handleImgElement(element: Element) = Some(ImageElement(element))

  private def handleTitleElement(element: Element) = {
    val titleElement = element.ownerDocument.select("title").first()
    if (titleElement != null) Some(TitleElement(titleElement)) else None
  }


  private def handleElement(element: Element) = {
    var textElement: Option[TextElement] = None
    if (isArticleContentTag(element.tag) && StringUtils.isNotBlank(element.ownText())) {
      textElement = Some(TextElement(element))
    }
    textElement
  }

}
