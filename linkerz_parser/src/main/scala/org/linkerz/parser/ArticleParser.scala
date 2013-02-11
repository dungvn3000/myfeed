package org.linkerz.parser

import processor._
import model.Article
import org.jsoup.nodes.Document
import org.linkerz.core.string.RichString._

/**
 * The Class ArticleParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:45 AM
 *
 */
class ArticleParser {

  val processors = new Processors <~ List(
    //Step1: Remove hidden element , clean document.
    new DocumentCleaner,
    new LanguageDetector,
    new RemoveHiddenElement,
    new RemoveDirtyElementFilter,
    //Step2: Extract article elements.
    new ArticleExtractor,
    new TitleExtractor,
    //Step3: Try to find potential element.
    new TitleBaseFilter,
    new RssDescriptionBaseFilter,
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
    new ExpandTitleToContentFilter,
    //Step6: Extract description from the content
    new DescriptionExtractor
  )

  /**
   * Parse a html document to an article
   * @param doc
   * @param title optional using like a hint for the parser
   * @param descriptionFromRss optional using like a hint for the parser
   * @return
   */
  def parse(doc: Document, title: String = "", descriptionFromRss: String = "") = {
    val article = Article(doc.normalise())
    if (title.isNotBlank) article.title = title.trimToEmpty
    if (descriptionFromRss.isNotBlank) article.descriptionFromRss = descriptionFromRss.trimToEmpty
    processors.process(article)
    article
  }

}
