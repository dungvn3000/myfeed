package org.linkerz.crawl.topology.parser.extractor

import net.htmlparser.jericho.{HTMLElementName, StartTag, Segment, TextExtractor}

/**
 * The Class CleanTextExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12 7:36 PM
 *
 */
class TitleTextExtractor(segment: Segment) extends TextExtractor(segment) {

  val htmlTagRegex = "<.*>"

  override def excludeElement(startTag: StartTag) = startTag != segment.getFirstStartTag

  def toTitle = this.toString.replaceAll(htmlTagRegex, "")
}

object TitleTextExtractor {

  implicit def warpAnElement(segment: Segment) = new TitleTextExtractor(segment)

}