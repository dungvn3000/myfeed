package org.linkerz.parser

import extractor.TitleExtractor
import model.Article
import org.jsoup.nodes.Document

/**
 * The Class ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:45 AM
 *
 */
class ArticleParser {

  /**
   * Parse a html document to an article
   * @param doc
   * @return
   */
  def parse(doc: Document) = {
    val article = new Article

    //Step1: Extract title.

    article.titleElement = TitleExtractor.extract(doc)

    println(article.titleElement.get.getTitleBaseOnPageContent)
    println(article.titleElement.get.getTitleBaseOnStopWord)

    //Step2: Convent html element to article element

    //Step3: Try to find potential element.

    //Step4: Clean dirty element

    article
  }

}
