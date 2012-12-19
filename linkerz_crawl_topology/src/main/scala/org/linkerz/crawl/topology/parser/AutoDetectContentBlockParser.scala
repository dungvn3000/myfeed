package org.linkerz.crawl.topology.parser

import org.linkerz.crawl.topology.job.CrawlJob
import net.htmlparser.jericho.{HTMLElementName, Source}
import java.io.ByteArrayInputStream
import collection.JavaConversions._
import org.apache.commons.lang.StringUtils

/**
 * The Class AutoDetectContentBlockParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/19/12 12:22 PM
 *
 */
class AutoDetectContentBlockParser extends Parser {
  def parse(crawlJob: CrawlJob) {

    val inputStream = new ByteArrayInputStream(crawlJob.result.get.content)
    val source = new Source(inputStream)
    source.fullSequentialParse()

    val element = source.getAllElements

    element.foreach(el => {
      if (el.getName != HTMLElementName.BODY) {
        val st = el.getTextExtractor.toString
        if (StringUtils.isNotBlank(st) && st.length > 30) {
          println(el.getDebugInfo + " " + el.getTextExtractor.toString)
        }
      }
    })


  }
}
