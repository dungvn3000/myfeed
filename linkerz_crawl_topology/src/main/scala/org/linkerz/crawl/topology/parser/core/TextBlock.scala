package org.linkerz.crawl.topology.parser.core

import net.htmlparser.jericho.{TextExtractor, HTMLElementName, StartTag, Element}
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
    override def excludeElement(startTag: StartTag) = startTag.getName == HTMLElementName.A
  }

  var stopWordCount = 0
  var wordCount = 0

  private val _st = element.getTextExtractor.toString.trim

  if (StringUtils.isNotBlank(_st)) {
    stopWordCount = _counter.count(_st)
    wordCount = _tokenizer(_st).size
  }

  def text = _extractor.toString

  def depth = element.getDepth

  def parent = element.getParentElement
}
