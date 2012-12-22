package org.linkerz.parser.extractor

import org.jsoup.nodes.Document
import org.linkerz.parser.model.TitleElement


/**
 * This class is using for extract title from title element.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 2:30 AM
 *
 */
object TitleExtractor {

  def extract(doc: Document): Option[TitleElement] = {
    var titleElement: Option[TitleElement] = None
    val element = doc.select("title").first()
    if (element != null) {
      titleElement = Some(TitleElement(element))
    }
    titleElement
  }

}
