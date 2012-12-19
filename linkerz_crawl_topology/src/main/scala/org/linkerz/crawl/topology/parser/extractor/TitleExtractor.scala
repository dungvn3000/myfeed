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
      title = title.replaceAll("\\|","-")
      title = title.replaceAll("»","-")

      if (title.contains("-")) {
        title = findSuitableTitle(DASH_SPLITTER.split(title).toList)
      }
      return title
    }
    ""
  }

  private def findSuitableTitle(potentialTitle: List[String]) = {
    var maxStopWord = 0
    var bestTitle = potentialTitle.head
    potentialTitle.foreach(title => {
      val count = _counter.count(title)
      if (count > maxStopWord) {
        maxStopWord = count
        bestTitle = title
      }
    })

    bestTitle.trim
  }

}
