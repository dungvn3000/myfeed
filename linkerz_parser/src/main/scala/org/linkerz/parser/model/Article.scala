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

  var titleElement: Option[TitleElement] = None

  var textElements: List[TextElement] = Nil

  var imageElements: List[ImageElement] = Nil

  var linkElements: List[LinkElement] = Nil

  var elements: List[ArticleElement] = Nil

}
