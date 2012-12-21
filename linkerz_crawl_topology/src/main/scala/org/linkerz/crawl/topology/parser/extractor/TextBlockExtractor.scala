package org.linkerz.crawl.topology.parser.extractor

import net.htmlparser.jericho._

/**
 * The Class ExcludeChildTextExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 12/21/12 12:42 AM
 *
 */
class TextBlockExtractor(segment: Segment) extends TextExtractor(segment) {
  override def excludeElement(startTag: StartTag) = startTag != segment.getFirstStartTag &&
    startTag.getName != HTMLElementName.P &&
    startTag.getName != HTMLElementName.SPAN &&
    startTag.getName != HTMLElementName.B &&
    startTag.getName != HTMLElementName.STRONG &&
    startTag.getName != HTMLElementName.I

  def extractText = this.toString
}

object TextBlockExtractor {
  implicit def warpElement(segment: Segment) = new TextBlockExtractor(segment)
}