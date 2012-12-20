package org.linkerz.crawl.topology.parser.core

import net.htmlparser.jericho.{TextExtractor, HTMLElementName, StartTag, Element}
import org.apache.commons.lang.StringUtils
import breeze.text.tokenize.JavaWordTokenizer
import breeze.text.transform.StopWordFilter

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
  private val _tokenizerWithStopWord = JavaWordTokenizer ~> StopWordFilter("vi")

  private val _extractor = new TextExtractor(element) {
    override def excludeElement(startTag: StartTag) = startTag.getName == HTMLElementName.A
  }
  private val _st = _extractor.toString.trim

  var stopWordCount = 0
  var wordCount = 0


  if (StringUtils.isNotBlank(_st)) {
    stopWordCount = _counter.count(_st)
    wordCount = _tokenizer(_st).size
  }

  def textEvaluate = _extractor.toString

  def text = element.getTextExtractor.toString

  def depth = element.getDepth

  def parent = element.getParentElement

  /**
   * Remove stop word and count how many time the word appear in side the element content.
   * @param st
   * @return
   */
  def count(st: String) = {
    val elementTexts = _tokenizerWithStopWord(element.getTextExtractor.toString)
    val words = _tokenizerWithStopWord(st).toList
    var count = 0

    elementTexts.foreach(text => {
      if (words.contains(text)) count += 1
    })

    count
  }

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
