package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import collection.JavaConversions._
import org.jsoup.nodes.TextNode
import org.apache.commons.lang.StringUtils

/**
 * The Class TidyDocumentProcessor.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 3:58 AM
 *
 */
class TidyDocumentProcessor extends Processor {
  def process(article: Article) {
    val doc = article.doc
    val elements = doc.getAllElements
    elements.foreach(element => {
      if (element.isBlock && !element.children.isEmpty
        && element.tagName() != "p" && !element.ownText().isEmpty) {
        element.childNodes.foreach(node => node match {
          case textNode: TextNode => if (StringUtils.isNotBlank(textNode.text)) {
            val warpElement = doc.createElement("p")
            warpElement.text(textNode.getWholeText)
            textNode.replaceWith(warpElement)
          }
          case _ => //ignore
        })
      }
    })
  }
}
