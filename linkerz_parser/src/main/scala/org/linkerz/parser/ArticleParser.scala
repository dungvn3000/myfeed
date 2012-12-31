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

  val processorsForAutoMode = new Processors <~ List(
    //Step1: Remove hidden element , clean document.
    new DocumentCleaner,
    new LanguageDetector,
    new RemoveHiddenElement,
    //Step2: Extract article elements.
    new ArticleExtractor,
    new TitleExtractor,
    //Step3: Try to find potential element.
    new TitleBaseFilter,
    new NumbOfWordFilter,
    new TagBaseFilter,
    new ImageBaseFilter,
    new DistanceBaseFilter,
    //Step4: Remove bad quality element.
    new DirtyImageFilter,
    new HighLinkDensityFilter,
    //Step5: Only keep high score elements.
    new HighestScoreElementFilter,
    new ContainerElementDetector,
    new ExpandTitleToContentFilter
  )

  val processorsForManualMode = new Processors <~ List(
    new DocumentCleaner,
    new LanguageDetector,
    new RemoveHiddenElement,
    new RemoveDirtyElementFilter,
    new ArticleExtractor,
    new TitleExtractor,
    new MarkEveryThingIsPotentialFilter,
    new DirtyImageFilter,
    new HighLinkDensityFilter,
    new MarkPotentialIsContentFilter
  )

  /**
   * Parse a html document to an article
   * @param doc
   * @return
   */
  def parse(doc: Document) = {
    val article = Article(doc.normalise())
    processorsForAutoMode.process(article)
    article
  }


  /**
   * Parse a html document to an article
   * @param doc
   * @param contentSelection
   * @param removeSelections
   * @return
   */
  def parse(doc: Document, contentSelection: String, removeSelections: List[String] = Nil): Option[Article] = {
    //Remove unused selections.
    removeSelections.foreach(doc.select(_).remove())
    val containerElement = doc.select(contentSelection).first()
    if (containerElement != null) {
      val article = Article(doc, Some(containerElement))
      processorsForManualMode.process(article)
      return Some(article)
    }
    None
  }

}
