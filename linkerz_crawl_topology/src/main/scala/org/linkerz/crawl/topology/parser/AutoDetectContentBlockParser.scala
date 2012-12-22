package org.linkerz.crawl.topology.parser

import cleaner._
import extractor.TitleExtractor
import filter.NumberOfWordRuleFilter
import net.htmlparser.jericho.Source
import java.io.ByteArrayInputStream
import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.parser.extractor.TextBlockExtractor._

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
    var source = new Source(inputStream)

    //Step2: Clean dirty elements
    source = Cleaner.clean(source)
    source.fullSequentialParse()

    //Step3: Extract page title
    val title = TitleExtractor.extract(source)

    //Step4: Auto detect content block
    if (title.isDefined) {
      val textBlocks = source.extractTextBlocks

      //Step4.1: Using design pattern pipe and filter to filter text block and mark the potential block is content
      NumberOfWordRuleFilter.filter(textBlocks)

      textBlocks.foreach(block => {
        println(block.text + " " + block.isPotentialBlock)
      })



      //
      //      if (!potentialBlocks.isEmpty) {
      //        val parentMap = new mutable.HashMap[TextBlock, Int].withDefaultValue(0)
      //        potentialBlocks.foreach(block => {
      //          parentMap(TextBlock(block.parent)) += block.score
      //        })
      //
      //        val bestBlock = parentMap.toList.sortWith(_._2 > _._2).map(_._1).head
      //
      //        info("title: " + title.get)
      //
      //        val imgElement = ImageExtractor.extract(bestBlock.element)
      //
      //        imgElement.map(img => {
      //          info("image: " + img.getAttributeValue("src"))
      //        })
      //
      //        info(bestBlock.element.getStartTag)
      //        info(bestBlock.text)
      //      }
    } else {
      info("Can't extract title form your article.")
    }
  }
}
