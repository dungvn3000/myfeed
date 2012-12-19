package org.linkerz.crawl.topology.parser

import cleaner._
import core.TextBlock
import net.htmlparser.jericho.{Element, HTMLElementName, Source}
import java.io.ByteArrayInputStream
import org.apache.commons.lang.StringUtils
import scala.collection.JavaConversions._
import collection.mutable.ListBuffer
import collection.mutable
import org.linkerz.crawl.topology.job.CrawlJob

/**
 * The Class AutoDetectContentBlockParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/19/12 12:22 PM
 *
 */
class AutoDetectContentBlockParser extends Parser {

  val minTextLength = 10
  val minStopWordCount = 3

  def parse(crawlJob: CrawlJob) {

    //Step1: Parser the html page.
    val inputStream = new ByteArrayInputStream(crawlJob.result.get.content)
    var source = new Source(inputStream)

    source = CommentCleaner.clean(source)
    source = SocialCleaner.clean(source)
    source = IFrameCleaner.clean(source)
    source = NavigationCleaner.clean(source)
    source = AddCleaner.clean(source)

    source.fullSequentialParse()

    val potentialBlocks = findPotentialBlock(source.getAllElements)

    val parentMap = new mutable.HashMap[Element, Int].withDefaultValue(1)
    potentialBlocks.foreach(block => parentMap(block.parent) += 1)

    val bestParent = parentMap.toList.sortWith(_._2 > _._2).head._1

    info(bestParent.getStartTag)

    info(bestParent.getTextExtractor.toString)
  }

  def findPotentialBlock(elements: java.util.List[Element]) = {
    val potentialBlocks = new ListBuffer[TextBlock]
    elements.foreach(el => {
      if (el.getName != HTMLElementName.BODY
        && el.getName != HTMLElementName.HTML
        && el.getName != HTMLElementName.A) {
        val textBlock = TextBlock(el)
        if (StringUtils.isNotBlank(textBlock.textEvaluate) && textBlock.textEvaluate.length > minTextLength) {
          if (textBlock.stopWordCount > minStopWordCount) {
            potentialBlocks += textBlock
          }
        }
      }
    })
    potentialBlocks.toList
  }
}
