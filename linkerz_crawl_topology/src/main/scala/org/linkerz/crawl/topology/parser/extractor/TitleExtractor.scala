package org.linkerz.crawl.topology.parser.extractor

import net.htmlparser.jericho.{CharacterReference, HTMLElementName, Source}
import scala.collection.JavaConversions._
import org.apache.commons.lang.StringUtils
import collection.mutable.ListBuffer

/**
 * This class is using for extract title from title element.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 2:30 AM
 *
 */
object TitleExtractor {

  val minTitleLength = 5

  def extract(source: Source): Option[String] = {
    val titleElement = source.getFirstElement(HTMLElementName.TITLE)
    if (titleElement != null) {
      val title = CharacterReference.decodeCollapseWhiteSpace(titleElement.getContent)
      return findSuitableTitle(title, source)
    }
    None
  }

  /**
   * Get the most meaningful title.
   * @param title
   * @return
   */
  private def findSuitableTitle(title: String, source: Source): Option[String] = {
    val potentialTitle = new ListBuffer[String]
    val elements = source.getAllElements
    elements.foreach(element => {
      if (element.getName != HTMLElementName.TITLE
        && element.getName != HTMLElementName.HEAD) {
        val text = element.getTextExtractor.toString
        if (StringUtils.isNotBlank(text) && text.length > minTitleLength && title.contains(text)) {
          potentialTitle += text
        }
      }
    })

    if (!potentialTitle.isEmpty) {
      val title = potentialTitle.sortWith(_.length > _.length).head
      return Some(title)
    }

    None
  }

}
