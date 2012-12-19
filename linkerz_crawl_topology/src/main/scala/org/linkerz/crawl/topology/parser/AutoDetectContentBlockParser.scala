package org.linkerz.crawl.topology.parser

import core.TextBlock
import org.linkerz.crawl.topology.job.CrawlJob
import net.htmlparser.jericho.{Element, HTMLElementName, Source}
import java.io.ByteArrayInputStream
import org.apache.commons.lang.StringUtils
import scala.collection.JavaConversions._
import collection.mutable.ListBuffer
import collection.mutable

/**
 * The Class AutoDetectContentBlockParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/19/12 12:22 PM
 *
 */
class AutoDetectContentBlockParser extends Parser {

  def parse(crawlJob: CrawlJob) {

    //Step1: Parser the html page.
    val inputStream = new ByteArrayInputStream(crawlJob.result.get.content)
    val source = new Source(inputStream)
    source.fullSequentialParse()

    val potentialBlocks = findPotentialBlock(source.getAllElements)
    val parentMap = new mutable.HashMap[Element, Int].withDefaultValue(0)
    potentialBlocks.foreach(block => parentMap(block.parent) += 1)

    val sort = parentMap.toList.sortWith((m1, m2) => {
      m1._2 > m2._2
    })

    val bestParent = sort.head._1

    sort.foreach(map => {
      println(map._1.getStartTag + " " + map._2)
    })

    println(bestParent.getStartTag)

    println(bestParent.getTextExtractor.toString)
  }

  def findPotentialBlock(elements: java.util.List[Element]) = {
    val potentialBlocks = new ListBuffer[TextBlock]
    elements.foreach(el => {
      if (el.getName != HTMLElementName.BODY
        && el.getName != HTMLElementName.HTML
        && el.getName != HTMLElementName.A) {
        val textBlock = TextBlock(el)
        if (StringUtils.isNotBlank(textBlock.text) && textBlock.text.length > 30) {
          if (textBlock.stopWordCount > 5) {
            potentialBlocks += textBlock
          }
        }
      }
    })
    potentialBlocks.toList
  }
}
