package org.linkerz.crawl.topology.parser.core

import net.htmlparser.jericho.{HTMLElements, TextExtractor, StartTag, Element}
import org.apache.commons.lang.StringUtils
import breeze.text.tokenize.JavaWordTokenizer

/**
 * This class represent for a text block inside a html page.
 * It using for scoring to decide witch block is content block.
 *
 * @author Nguyen Duc Dung
 * @since 12/19/12, 6:11 PM
 */

case class TextBlock(element: Element) {

  private val _counter = new StopWordCounter("vi")
  private val _tokenizer = JavaWordTokenizer
  private val _extractor = new TextExtractor(element) {
    //Extract text with out child element
    override def excludeElement(startTag: StartTag) = startTag != element.getFirstStartTag &&
      !HTMLElements.getInlineLevelElementNames.contains(startTag.getName)
  }

  private val _textWithoutChild = _extractor.toString

  var stopWordCount = 0
  var wordCount = 0
  var isPotentialBlock = false

  if (StringUtils.isNotBlank(_textWithoutChild)) {
    stopWordCount = _counter.count(_textWithoutChild)
    wordCount = _tokenizer(_textWithoutChild).size
  }

  def textWithoutChild = _textWithoutChild

  def text = element.getTextExtractor.toString

  def depth = element.getDepth

  def parent = element.getParentElement

  /**
   * Score to evaluate this block.
   * @return
   */
  def score = wordCount * stopWordCount

  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[TextBlock]) {
      val element2 = obj.asInstanceOf[TextBlock].element
      element.equals(element2)
    } else {
      false
    }
  }

  override def hashCode() = element.hashCode()
}
