package org.linkerz.crawl.topology.parser.cleaner

import net.htmlparser.jericho.{OutputDocument, Source}
import java.util.regex.Pattern

/**
 * The Class Cleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 1:30 AM
 *
 */
object Cleaner {

  val addPattern = Pattern.compile(".*banner.*|.*quangcao.*", Pattern.CASE_INSENSITIVE)

  val commentPattern = Pattern.compile(".*comment.*|user*|.*avatar.*|member.*", Pattern.CASE_INSENSITIVE)

  val navPattern = Pattern.compile("breadcrumb|crumbs|.*navlink.*|pagenav.*|.*page_nav.*|pager|phantrang|.*menu.*|.*bookmark.*",
    Pattern.CASE_INSENSITIVE)

  val socialPattern = Pattern.compile(".*like.*|.*vote.*|.*share.*|.*facebook.*|.*twitter.*|.*google.*|linkhay|sociable",
    Pattern.CASE_INSENSITIVE)

  def patterns = List(addPattern, commentPattern, navPattern, socialPattern)

  def clean(source: Source) = {

    val outputDocument = new OutputDocument(source)

    patterns.foreach(pattern => {
      val classElements = source.getAllElements("class", pattern)
      val idElements = source.getAllElements("id", pattern)
      val nameElements = source.getAllElements("name", pattern)

      outputDocument.remove(classElements)
      outputDocument.remove(idElements)
      outputDocument.remove(nameElements)
    })

    val cleanedSource = new Source(outputDocument.getSourceText)
    cleanedSource.fullSequentialParse()

    cleanedSource
  }

}
