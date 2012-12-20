package org.linkerz.crawl.topology.parser.cleaner

import net.htmlparser.jericho.{HTMLElementName, CharStreamSourceUtil, OutputDocument, Source}
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

  val navPattern = Pattern.compile(".*breadcrumb.*|crumbs|.*navlink.*|pagenav.*|.*page_nav.*|pager|" +
    "phantrang|.*menu.*|.*bookmark.*|header|.*footer.*|.*navigation.*",
    Pattern.CASE_INSENSITIVE)

  val socialPattern = Pattern.compile(".*like.*|.*vote.*|.*share.*|.*rating.*|.*facebook.*|.*twitter.*|.*google.*|linkhay|sociable",
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

    //Remove iframe, cause most of them is adv.
    val iframeElements = source.getAllElements(HTMLElementName.IFRAME)
    outputDocument.remove(iframeElements)

    //Remove script tag
    val scriptElements = source.getAllElements(HTMLElementName.SCRIPT)
    outputDocument.remove(scriptElements)

    //Remove noscript tag
    val noscriptElements = source.getAllElements(HTMLElementName.NOSCRIPT)
    outputDocument.remove(noscriptElements)

    //Remove style tag
    val styleElements = source.getAllElements(HTMLElementName.STYLE)
    outputDocument.remove(styleElements)

    val reader = CharStreamSourceUtil.getReader(outputDocument)
    val cleanedSource = new Source(reader)
    cleanedSource
  }

}
