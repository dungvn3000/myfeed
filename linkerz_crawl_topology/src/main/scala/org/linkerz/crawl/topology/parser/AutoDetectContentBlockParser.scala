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

    val parentMap = new mutable.HashMap[Element, Int].withDefaultValue(1)
    potentialBlocks.foreach(block => parentMap(block.parent) += 1)

    val bestParent = parentMap.toList.sortWith(_._2 > _._2).head._1

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
        if (StringUtils.isNotBlank(textBlock.textEvaluate) && textBlock.textEvaluate.length > 30) {
          if (textBlock.stopWordCount > 3) {
            potentialBlocks += textBlock
          }
        }
      }
    })
    potentialBlocks.toList
  }
}
