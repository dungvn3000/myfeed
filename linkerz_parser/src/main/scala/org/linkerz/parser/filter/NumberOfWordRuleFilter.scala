package org.linkerz.crawl.topology.parser.filter


/**
 * This filter will base on the number of word.
 *
 * @author Nguyen Duc Dung
 * @since 12/22/12 5:56 PM
 *
 */
object NumberOfWordRuleFilter extends Filter {

  val minNumbOfWord = 10
  val minNumbOfStopWord = 1

  def filter(textBlocks: List[TextElement]) {

  }
}
