package org.linkerz.crawl.topology.parser.extractor

import com.gravity.goose.text.StringSplitter
import net.htmlparser.jericho.{CharacterReference, HTMLElementName, Source}

/**
 * This class is using for extract title from title element.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 2:30 AM
 *
 */
object TitleExtractor {

  val DASH_SPLITTER: StringSplitter = new StringSplitter("-")

  def extract(source: Source): String = {
    val titleElement = source.getFirstElement(HTMLElementName.TITLE)
    if (titleElement != null) {
      var title = CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent)
      title = title.replaceAll("\\|","-")
      title = title.replaceAll("»","-")

      if (title.contains("-")) {
        title = findSuitableTitle(DASH_SPLITTER.split(title).toList)
      }
      return title
    }
    ""
  }

  /**
   * Get the longest title.
   * @param potentialTitle
   * @return
   */
  private def findSuitableTitle(potentialTitle: List[String]) = {
    var maxLength = 0
    var bestTitle = potentialTitle.head
    potentialTitle.foreach(title => {
      if (title.length > maxLength) {
        maxLength = title.length
        bestTitle = title
      }
    })

    bestTitle.trim
  }

}
