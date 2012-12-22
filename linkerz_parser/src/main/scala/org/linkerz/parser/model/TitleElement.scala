package org.linkerz.parser.model

import org.jsoup.nodes.Element
import collection.mutable.ListBuffer
import collection.JavaConversions._
import org.apache.commons.lang.StringUtils
import org.linkerz.crawl.topology.parser.util.StopWordCounter

/**
 * The Class TitleBlock.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:05 AM
 *
 */
case class TitleElement(element: Element) {

  /**
   * This method to get the title base on element content. In most of case the title will appear in two places,
   * the first one is in document title and the second one on top of the article content. But the title in the title tag is
   * not only title of the article, it usually include another text such as domain name, article category.
   * So we try to get title in document title and search inside the document to find the correct title.
   * @return
   */
  def getTitleBaseOnPageContent: Option[String] = {
    val potentialTitle = new ListBuffer[String]
    val elements = element.ownerDocument().getAllElements
    val title = element.ownerDocument.title()

    elements.foreach(element => {
      if (element.tagName() != "title"
        && element.tagName() != "head") {
        val text = element.text.trim
        if (StringUtils.isNotBlank(text) && title.contains(text)) {
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

  /**
   * Get title base on stop word and length of the title
   * @return
   */
  def getTitleBaseOnStopWord: String = {
    var bestTitle = element.ownerDocument.title()
    var candidateTitle = bestTitle
    if (StringUtils.isNotBlank(candidateTitle)) {
      candidateTitle = candidateTitle.replaceAll("\\|", "-")
      candidateTitle = candidateTitle.replaceAll("\\/", "-")
      candidateTitle = candidateTitle.replaceAll("\\\\", "-")
      candidateTitle = candidateTitle.replaceAll("\\//", "-")
      candidateTitle = candidateTitle.replaceAll("»", "-")

      if (candidateTitle.contains("-")) {
        val potentialTitles = candidateTitle.split("-")

        val counter = new StopWordCounter("vi")
        var maxScore = 0

        potentialTitles.foreach(potentialTitle => {
          val score = (counter.count(potentialTitle) + 1) * potentialTitle.length
          if (score > maxScore) {
            maxScore = score
            bestTitle = StringUtils.strip(potentialTitle)
          }
        })
      }
    }
    bestTitle
  }

}
