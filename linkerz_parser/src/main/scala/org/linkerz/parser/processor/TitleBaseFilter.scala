package org.linkerz.parser.processor

import org.linkerz.parser.model.Article

/**
 * This class will base on the title to find potential element.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 8:13 AM
 *
 */
class TitleBaseFilter extends Processor {
  def process(implicit article: Article) {
    article.titleElement.map(title => {
      article.elements.foreach(element => if (element.text == title.text && element != title) {
        element.isPotential = true
      })
    })
  }
}
