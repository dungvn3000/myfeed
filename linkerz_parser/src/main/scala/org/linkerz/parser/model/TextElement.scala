package org.linkerz.parser.model

import breeze.text.tokenize.JavaWordTokenizer
import org.jsoup.nodes.Element
import org.linkerz.crawl.topology.parser.util.StopWordCounter
import org.apache.commons.lang.StringUtils
import ElementWrapper._

/**
 * This class represent for a text block inside a html page.
 * It using for scoring to decide witch block is content block.
 *
 * @author Nguyen Duc Dung
 * @since 12/19/12, 6:11 PM
 */

case class TextElement(element: Element) extends ArticleElement {

  private val _counter = new StopWordCounter("vi")
  private val _tokenizer = JavaWordTokenizer
  private val _text = if (element.detectTextBlock) element.text() else element.ownText()

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
      val element2 = obj.asInstanceOf[TextElement].element
      element.equals(element2)
    } else {
      false
    }
  }

  override def hashCode() = element.hashCode()
}
