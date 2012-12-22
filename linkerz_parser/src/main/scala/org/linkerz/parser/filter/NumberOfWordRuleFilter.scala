package org.linkerz.parser.filter

import org.linkerz.parser.model.TextElement

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
