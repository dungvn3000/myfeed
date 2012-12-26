package org.linkerz.parser.model

import org.jsoup.nodes.Element
import collection.JavaConversions._
import java.util.regex.Pattern

/**
 * The Class to warp jsoup element to add more function to it.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 11:28 PM
 *
 */
class JsoupElementWrapper(element: Element) {

  val hiddenStyle = Pattern.compile(".*display: none.*|" +
    ".*visibility: hidden.*|.*bottom: -.*px.*|.*left: -.*px.*|.*top: -.*px.*|.*right: -.*px.*", Pattern.CASE_INSENSITIVE)

  /**
   * Which an element has attribute skip-parser is true, was marked by processor,
   * will be skip when parsing.
   * @return
   */
  def isSkipParse = element.attr("skip-parse") == "true"

  def isSkipParse_=(skip: Boolean) {
    element.attr("skip-parse", skip.toString)
  }

  /**
   * Check this element whether should get it's text or it's own text.
   * @return
   */
  def detectTextBlock = {
    var allIsInLineElement = true
    element.children.foreach(child => {
      if (child.isBlock || child.tagName == "a" || child.tagName == "select") {
        allIsInLineElement = false
      }
    })
    !element.ownText.isEmpty || allIsInLineElement
  }


  /**
   * Check whether the element is hidden or not.
   * @return
   */
  def isHidden = hiddenStyle.matcher(element.attr("style")).matches()

  /**
   * Same with method getAllElements but exclude it's self.
   * @return
   */
  def innerAllElements = element.getAllElements.filter(_ != element)
}

object JsoupElementWrapper {

  implicit def warp(element: Element) = new JsoupElementWrapper(element)

}