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
    //Step1: Find bad tag and try to fix it.
    new TidyDocumentProcessor,
    //Step2: Process the article.
    new ArticleProcessor,
    //Step3: Try to find potential element.
    new NumbOfWordFilter
    //Step4: Clean dirty element
  )

  /**
   * Parse a html document to an article
   * @param doc
   * @return
   */
  def parse(doc: Document) = {
    val article = Article(doc.normalise())
    processors.process(article)
    article
  }

}
