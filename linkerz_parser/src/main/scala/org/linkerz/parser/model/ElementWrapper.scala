package org.linkerz.parser.model

import org.jsoup.nodes.Element
import collection.JavaConversions._

/**
 * The Class to warp jsoup element to add more function to it.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 11:28 PM
 *
 */
class ElementWrapper(element: Element) {

  /**
   * Which an element has attribute skip-parser is true, was marked by processor,
   * will be skip when parsing.
   * @return
   */
  def shouldSkipParse = element.attr("skip-parse") == "true"

  def shouldSkipParse_=(skip: Boolean) {
    element.attr("skip-parse", skip.toString)
  }

  /**
   * Check this element whether should get it's text or it's own text.
   * @return
   */
  def detectTextBlock = element.isBlock && !element.ownText.isEmpty

  /**
   * Same with method getAllElements but exclude it's self.
   * @return
   */
  def getInnerAllElements = element.getAllElements.filter(_ != element)
}

object ElementWrapper {

  implicit def warp(element: Element) = new ElementWrapper(element)

}