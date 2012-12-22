package org.linkerz.crawl.topology.parser.extractor

import net.htmlparser.jericho._
import collection.mutable.ListBuffer
import org.linkerz.crawl.topology.parser.core.TextBlock
import org.apache.commons.lang.StringUtils
import collection.JavaConversions._

/**
 * The Class ExcludeChildTextExtractor.
 *
 * @author Nguyen Duc Dung
 * @since 12/21/12 12:42 AM
 *
 */
class TextBlockExtractor(source: Source) {

  def extractTextBlocks = {
    val textBlocks = new ListBuffer[TextBlock]
    val elements = source.getAllElements
    elements.foreach(el => {
      if (el.getName != HTMLElementName.BODY
        && el.getName != HTMLElementName.HTML
        && el.getName != HTMLElementName.TITLE
        && el.getName != HTMLElementName.HEAD
        && el.getName != HTMLElementName.META
        && !HTMLElements.getInlineLevelElementNames.contains(el.getName)) {
        val textBlock = TextBlock(el)
        if (StringUtils.isNotBlank(textBlock.textWithoutChild)) {
          textBlocks += textBlock
        }
      }
    })

    textBlocks.toList
  }

  def extractText = this.toString
}

object TextBlockExtractor {
  implicit def warpSource(source: Source) = new TextBlockExtractor(source)
}