package org.linkerz.parser.model

import org.jsoup.nodes.Element

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
  def isSkipParser = element.attr("skip-parser") == "true"

  def isSkipParser_=(skip: Boolean) {
    element.attr("skip-parser", skip.toString)
  }
}

object ElementWrapper {

  implicit def warp(element: Element) = new ElementWrapper(element)

}