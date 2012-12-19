package org.linkerz.crawl.topology.parser.extractor

import net.htmlparser.jericho.{HTMLElementName, Element}
import scala.collection.JavaConversions._

/**
 * The Class ImageExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 3:18 AM
 *
 */
object ImageExtractor {

  def extract(contentElement: Element): Option[Element] = {
    val potentialElement = contentElement.getAllElements(HTMLElementName.IMG)
    findTheBestImage(potentialElement.toList)
  }

  /**
   * Mostly the suitable image always have the url is very long.
   * Because the image name should avoid duplicate name.
   * @param potentialElement
   * @return
   */
  private def findTheBestImage(potentialElement: List[Element]): Option[Element] = {
    var bestImage: Option[Element] = None
    var maxSrc = 0
    potentialElement.foreach(element => {
      val src = element.getAttributeValue("src")
      if (src.length > maxSrc && !src.contains(".gif")) {
        maxSrc = src.length
        bestImage = Some(element)
      }
    })
    bestImage
  }
}
