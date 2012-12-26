package org.linkerz.parser.processor

import org.linkerz.parser.model.Article

/**
 * The main idea came from Boilerpipe framework http://code.google.com/p/boilerpipe/.
 *
 * @author Nguyen Duc Dung
 * @since 12/26/12 11:54 AM
 *
 */
class ExpandTitleToContentFilter extends Processor {
  def process(implicit article: Article) {
    if (article.contentElements.size > 2) {
      article.elements.find(_.isTitle).map(title => {
        val titleIndex = title.index
        val contentIndex = article.contentElements.head.index
        for (i <- titleIndex to contentIndex if (article.elements(i).isPotential)) {
          article.elements(i).isContent = true
        }
      })
    }
  }
}
