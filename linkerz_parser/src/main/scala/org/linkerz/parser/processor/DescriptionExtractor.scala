package org.linkerz.parser.processor

import org.linkerz.parser.model.Article
import org.linkerz.core.string.RichString._

/**
 * The short description text for the article. Find the longest block and split it to a description.
 *
 * @author Nguyen Duc Dung
 * @since 2/10/13 2:51 PM
 *
 */
class DescriptionExtractor(maxNumbOfWord: Int = 30) extends Processor {
  def process(implicit article: Article) {

    //Skip it when the description is already defined.
    if (article.description.isNotBlank) return

    var bestDescription: String = ""
    if (!article.textContentElements.isEmpty) {
      bestDescription = article.textContentElements.sortBy(-_.text.length).head.text
      val words = bestDescription.split(' ')
      val sb = new StringBuilder
      var wordCount = 0
      words.foreach(word => {
        if (wordCount < maxNumbOfWord) {
          sb.append(word)
          sb.append(" ")
          wordCount += 1
        }
      })
      bestDescription = sb.toString()
    }
    article.description = bestDescription
  }
}
