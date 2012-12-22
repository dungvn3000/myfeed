package org.linkerz.crawl.topology.parser.extractor


/**
 * This class is using for extract title from title element.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 2:30 AM
 *
 */
object TitleExtractor {

//  val minTitleLength = 5
//  val DASH_SPLITTER: StringSplitter = new StringSplitter("-")
//
//  def extract(source: Source): Option[String] = {
//    val titleElement = source.getFirstElement(HTMLElementName.TITLE)
//    if (titleElement != null) {
//      val title = titleElement.toTitle
//      val result = findTitleBaseOnPageContent(title, source)
//
//      if (result.isEmpty) {
//        return Some(findTitleBaseOnItSelf(title, source))
//      } else {
//        return result
//      }
//    }
//    None
//  }
//
//  /**
//   * Get the most meaningful title.
//   * @param title
//   * @return
//   */
//  private def findTitleBaseOnPageContent(title: String, source: Source): Option[String] = {
//    val potentialTitle = new ListBuffer[String]
//    val elements = source.getAllElements
//
//    elements.foreach(element => {
//      if (element.getName != HTMLElementName.TITLE
//        && element.getName != HTMLElementName.HEAD) {
//        val text = element.toTitle
//
//        if (StringUtils.isNotBlank(text) && text.length > minTitleLength
//          && title.contains(StringUtils.strip(text))) {
//          potentialTitle += text
//        }
//      }
//    })
//
//    if (!potentialTitle.isEmpty) {
//      val title = potentialTitle.sortWith(_.length > _.length).head
//      return Some(title)
//    }
//
//    None
//  }
//
//  private def findTitleBaseOnItSelf(title: String, source: Source): String = {
//    var bestTitle = title
//    var candidateTitle = title
//    if (StringUtils.isNotBlank(candidateTitle)) {
//      candidateTitle = candidateTitle.replaceAll("\\|", "-")
//      candidateTitle = candidateTitle.replaceAll("\\/", "-")
//      candidateTitle = candidateTitle.replaceAll("\\\\", "-")
//      candidateTitle = candidateTitle.replaceAll("\\//", "-")
//      candidateTitle = candidateTitle.replaceAll("»", "-")
//
//      if (candidateTitle.contains("-")) {
//        val potentialTitles = DASH_SPLITTER.split(candidateTitle).toList
//
//        val counter = new StopWordCounter("vi")
//        var maxScore = 0
//
//        potentialTitles.foreach(potentialTitle => {
//          val pageText = source.getTextExtractor.toString
//
//          var score = 0
//          if (pageText.contains(potentialTitle)) {
//            //Add one plus point for this case.
//            score = (counter.count(potentialTitle) + 2) * potentialTitle.length
//          } else {
//            score = (counter.count(potentialTitle) + 1) * potentialTitle.length
//          }
//
//          if (score > maxScore) {
//            maxScore = score
//            bestTitle = StringUtils.strip(potentialTitle)
//          }
//        })
//      }
//    }
//    bestTitle
//  }

}
