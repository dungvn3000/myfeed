package org.linkerz.crawl.topology.parser.core

import net.htmlparser.jericho.{Segment, TextExtractor}

/**
 * The Class CleanTextExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12 7:36 PM
 *
 */
class CleanTextExtractor(segment: Segment) extends TextExtractor(segment) {

  val htmlTagRegex = "<.*>"

  def toCleanText = super.toString.replaceAll(htmlTagRegex, "")
}

object CleanTextExtractor {

  implicit def warpAnElement(segment: Segment) = new CleanTextExtractor(segment)

}