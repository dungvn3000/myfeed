package org.linkerz.crawl.topology.parser.filter

import org.linkerz.crawl.topology.parser.core.TextBlock

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

  def filter(textBlocks: List[TextBlock]) {
    textBlocks.foreach(block => {
      if (block.stopWordCount > minNumbOfStopWord && block.wordCount > minNumbOfWord) {
        block.isPotentialBlock = true
      }
    })
  }
}
