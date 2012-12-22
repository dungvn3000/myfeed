package org.linkerz.parser.model


/**
 * The Class Article.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:00 AM
 *
 */
class Article {

  var titleElement: Option[TitleElement] = None

  var textElements: List[TextElement] = Nil

  var imageElements: List[ImageElement] = Nil

  var linkElements: List[LinkElement] = Nil

}
