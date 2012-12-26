package org.linkerz.parser.processor

import org.linkerz.parser.model.{ArticleElement, Article}
import collection.mutable

/**
 * This class will remove all element is not article element.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 2:17 PM
 *
 */
class HighestScoreElementFilter extends Processor {
  def process(implicit article: Article) {
    //Clustering potential elements has continuous index.
    if (!article.potentialElements.isEmpty) {
      val elementMap = new mutable.HashMap[Int, List[ArticleElement]].withDefaultValue(Nil)
      val scoreMap = new mutable.HashMap[Int, Double].withDefaultValue(0)
      var key = 0
      var lastIndex = article.potentialElements.head.index
      article.potentialElements.foreach(element => {
        if (element.index != lastIndex + 1) key += 1
        elementMap(key) ::= element
        lastIndex = element.index
      })

      //Sum all child element score to find the best block.
      elementMap.foreach(item => {
        val elements = item._2
        elements.foreach(scoreMap(item._1) += _.score)
      })

      val highestKey = scoreMap.toList.sortWith(_._2 > _._2).head._1

      elementMap(highestKey).foreach(_.isContent = true)
    }
  }
}
