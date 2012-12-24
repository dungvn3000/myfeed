package org.linkerz.parser.model

import org.jsoup.nodes.Element
import collection.mutable.ListBuffer
import collection.JavaConversions._
import org.apache.commons.lang.StringUtils
import org.linkerz.parser.util.StopWordCounter

/**
 * The Class TitleBlock.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 1:05 AM
 *
 */
case class TitleElement(element: Element) extends ArticleElement {

  var minTitleLength = 3


  def text = title.getOrElse("")

  /**
   * This method to get the title base on element content. In most of case the title will appear in two places,
   * the first one is in document title and the second one on top of the article content. But the title in the title tag is
   * not only title of the article, it usually include another text such as domain name, article category.
   * So we try to get title in document title and search inside the document to find the correct title.
   * @return
   */
  def title: Option[String] = {
    val potentialTitles = new ListBuffer[String]
    val elements = element.ownerDocument().getAllElements
    val title = element.ownerDocument.title()

    //Step1: Try to find potential by checking page content.
    elements.foreach(element => {
      if (element.tagName() != "title"
        && element.tagName() != "head") {
        val text = StringUtils.strip(element.text)
        if (StringUtils.isNotBlank(text)
          && title.contains(text) && text.length > minTitleLength) {
          potentialTitles += text
        }
      }
    })

    //Step2: Find potential title by split the title. Just in case step 1 can't found any potential title.
    if (potentialTitles.isEmpty) {
      var candidateTitle = title
      if (StringUtils.isNotBlank(candidateTitle)) {
        candidateTitle = candidateTitle.replaceAll("\\|", "-")
        candidateTitle = candidateTitle.replaceAll("\\/", "-")
        candidateTitle = candidateTitle.replaceAll("\\\\", "-")
        candidateTitle = candidateTitle.replaceAll("\\//", "-")
        candidateTitle = candidateTitle.replaceAll("»", "-")
        candidateTitle = candidateTitle.replaceAll(":", "-")

        if (candidateTitle.contains("-")) {
          potentialTitles ++= candidateTitle.split("-")
        }
      }
    }

    //Step3: Find the best title base on stop word.
    val bestTitle = getTitleByStopWord(potentialTitles.toList)

    if (StringUtils.isNotBlank(bestTitle)) Some(bestTitle) else None
  }

  private def getTitleByStopWord(potentialTitles: List[String]) = {
    var bestTitle = element.ownerDocument.title()
    val counter = new StopWordCounter("vi")
    var maxScore = 0

    potentialTitles.foreach(potentialTitle => {
      val score = (counter.count(potentialTitle) + 1) * potentialTitle.length
      if (score > maxScore) {
        maxScore = score
        bestTitle = potentialTitle
      }
    })
    StringUtils.strip(bestTitle)
  }

}
