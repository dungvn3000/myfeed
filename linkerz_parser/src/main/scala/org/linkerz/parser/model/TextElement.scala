package org.linkerz.parser.model

import breeze.text.tokenize.JavaWordTokenizer
import org.jsoup.nodes.Element
import org.apache.commons.lang.StringUtils
import JsoupElementWrapper._
import org.linkerz.parser.util.StopWordCounter

/**
 * This class represent for a text block inside a html page.
 * It using for scoring to decide witch block is content block.
 *
 * @author Nguyen Duc Dung
 * @since 12/19/12, 6:11 PM
 */

case class TextElement(_jsoupElement: Element) extends ArticleElement(_jsoupElement) {

  private val _counter = new StopWordCounter("vi")
  private val _tokenizer = JavaWordTokenizer
  private val _text = if (jsoupElement.detectTextBlock) jsoupElement.text() else jsoupElement.ownText()

  var stopWordCount = 0
  var wordCount = 0

  if (StringUtils.isNotBlank(_text)) {
    stopWordCount = _counter.count(_text)
    wordCount = _tokenizer(_text).size
  }

  def text = _text

  def hasText = StringUtils.isNotBlank(text)

  /**
   * Score to evaluate this block.
   * @return
   */
  def score = wordCount * stopWordCount

  override def equals(obj: Any): Boolean = {
    if (obj.isInstanceOf[TextElement]) {
      val element2 = obj.asInstanceOf[TextElement].jsoupElement
      jsoupElement.equals(element2)
    } else {
      false
    }
  }

  override def hashCode() = jsoupElement.hashCode()
}
