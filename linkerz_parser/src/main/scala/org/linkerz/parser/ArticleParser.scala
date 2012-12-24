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
    //Step1: Extract article elements.
    new ArticleExtractor,
    //Step2: Try to find potential element.
    new TitleBaseFilter,
    new NumbOfWordFilter,
    new ImageBaseFilter,
    new DistanceBaseFilter,
    //Step3: Only keep high score elements.
    new RemoveLowerScoreElementFilter,
    //Step4: Find image for the article.
    new RemoveDirtyImageFilter,
    new RemoveHighLinkDensityElement
  )

  /**
   * Parse a html document to an article
   * @param doc
   * @return
   */
  def parse(doc: Document) = {
    val article = Article(doc.normalise())
    processors.process(article)
    println("Title: " + article.title)
    println(article.prettyText)
    article.images.foreach(println)
    article
  }

}
