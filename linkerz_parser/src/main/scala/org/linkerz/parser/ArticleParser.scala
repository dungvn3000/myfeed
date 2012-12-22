package org.linkerz.parser

import processor._
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

  val processors = new Processors <~ List(
    TidyDocumentProcessor,
    ArticleProcessor
  )

  /**
   * Parse a html document to an article
   * @param doc
   * @return
   */
  def parse(doc: Document) = {

    val article = Article(doc.normalise())

    //Step1: Process the article.
    processors.process(article)

    //Step2: Try to find potential element.

    //Step4: Clean dirty element

    article
  }

}
