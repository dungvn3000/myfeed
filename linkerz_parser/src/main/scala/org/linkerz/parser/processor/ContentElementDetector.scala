package org.linkerz.parser.processor

import org.linkerz.parser.model.{TextElement, ArticleElement, Article}
import collection.mutable
import collection.JavaConversions._
import org.linkerz.parser.model.JsoupElementWrapper._

/**
 * This class will find the best content elements base on potential blocks.
 *
 * @author Nguyen Duc Dung
 * @since 12/24/12 2:17 PM
 *
 */
class ContentElementDetector extends Processor {
  def process(article: Article) {
    //Clustering potential elements has continuous index.
    val elementMap = new mutable.HashMap[Int, List[ArticleElement]].withDefaultValue(Nil)
    val scoreMap = new mutable.HashMap[Int, Int].withDefaultValue(0)

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
      elements.foreach(element => if (element.isInstanceOf[TextElement]) {
        scoreMap(item._1) += element.asInstanceOf[TextElement].score
      })
    })

    val highestKey = scoreMap.toList.sortWith(_._2 > _._2).head._1

    val articleElements = elementMap(highestKey)

    //Reset article element.
    article.elements = articleElements

    //Find the smallest element can contains all of them.
    val allElements = article.doc.getAllElements
    var smallestParent = allElements.head
    allElements.foreach(element => if (element.innerAllElements.containsAll(article.jsoupElements)) smallestParent = element)

    smallestParent
  }
}
