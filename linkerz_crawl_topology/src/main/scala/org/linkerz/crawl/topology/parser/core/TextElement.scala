package org.linkerz.crawl.topology.parser.core

import breeze.text.tokenize.JavaWordTokenizer
import org.jsoup.nodes.Element

/**
 * This class represent for a text block inside a html page.
 * It using for scoring to decide witch block is content block.
 *
 * @author Nguyen Duc Dung
 * @since 12/19/12, 6:11 PM
 */

case class TextElement(element: Element) {

  private val _counter = new StopWordCounter("vi")
  private val _tokenizer = JavaWordTokenizer

//  private val _textWithoutChild = {
//    _extractor.toString
//  }

  var stopWordCount = 0
  var wordCount = 0
  var isPotentialBlock = false

//  if (StringUtils.isNotBlank(_textWithoutChild)) {
//    stopWordCount = _counter.count(_textWithoutChild)
//    wordCount = _tokenizer(_textWithoutChild).size
//  }

//  def textWithoutChild = _textWithoutChild

  /**
   * Score to evaluate this block.
   * @return
   */
  def score = wordCount * stopWordCount

  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[TextElement]) {
      val element2 = obj.asInstanceOf[TextElement].element
      element.equals(element2)
    } else {
      false
    }
  }

  override def hashCode() = element.hashCode()
}
