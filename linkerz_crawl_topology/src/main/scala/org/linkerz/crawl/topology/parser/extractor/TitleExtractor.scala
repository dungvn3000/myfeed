package org.linkerz.crawl.topology.parser.extractor

import com.gravity.goose.text.StringSplitter
import net.htmlparser.jericho.{CharacterReference, HTMLElementName, Source}
import org.linkerz.crawl.topology.parser.core.StopWordCounter

/**
 * This class is using for extract title from title element.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 2:30 AM
 *
 */
object TitleExtractor {

  private val _counter = new StopWordCounter("vi")

  val DASH_SPLITTER: StringSplitter = new StringSplitter("-")

  def extract(source: Source): String = {
    val titleElement = source.getFirstElement(HTMLElementName.TITLE)
    if (titleElement != null) {
      var title = CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent)
      title = title.replaceAll("\\|", "-")
      title = title.replaceAll("\\/", "-")
      title = title.replaceAll("\\\\", "-")
      title = title.replaceAll("\\//", "-")
      title = title.replaceAll("»", "-")

      if (title.contains("-")) {
        title = findSuitableTitle(DASH_SPLITTER.split(title).toList, source)
      }
      return title
    }
    ""
  }

  /**
   * Get the most meaningful title.
   * @param potentialTitle
   * @return
   */
  private def findSuitableTitle(potentialTitle: List[String], source: Source) = {
    var maxScore = 0
    var bestTitle = potentialTitle.head
    potentialTitle.foreach(title => {

      val pageText = source.getTextExtractor.toString

      var score = 0
      if (pageText.contains(title)) {
        //Add one plus point for this case.
        score = (_counter.count(title) + 2) * title.length
      } else {
        score = (_counter.count(title) + 1) * title.length
      }

      if (score > maxScore) {
        maxScore = score
        bestTitle = title
      }
    })

    bestTitle.trim
  }

}
