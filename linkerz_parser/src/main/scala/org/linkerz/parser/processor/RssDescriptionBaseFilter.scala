package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import org.linkerz.core.string.RichString._
import org.jsoup.Jsoup
import collection.JavaConversions._

/**
 * This class will base on the description to find potential element.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 8:13 AM
 *
 */
class RssDescriptionBaseFilter extends Processor {
  def process(implicit article: Article) {
    if (article.descriptionFromRss.isNotBlank) {
      val doc = Jsoup.parse(article.descriptionFromRss)
      article.description = doc.text()

      doc.getAllElements.foreach(el1 => {
        article.elements.foreach(el2 => {
          if (el1.text.isNotBlank && el2.text.isNotBlank) {
            if (el1.text.equalsIgnoreCaseAndSpace(el2.text)) {
              el2.isPotential = true
            }
          }
        })
      })

    }
  }
}
