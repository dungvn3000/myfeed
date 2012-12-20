package org.linkerz.crawl.topology.parser.cleaner

import net.htmlparser.jericho.{HTMLElementName, CharStreamSourceUtil, OutputDocument, Source}

/**
 * The Class Cleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 1:30 AM
 *
 */
object Cleaner {

  def clean(source: Source) = {
    val outputDocument = new OutputDocument(source)

    val pattern = RemoveFilter.pattern
    val classElements = source.getAllElements("class", pattern)
    val idElements = source.getAllElements("id", pattern)
    val nameElements = source.getAllElements("name", pattern)

    outputDocument.remove(classElements)
    outputDocument.remove(idElements)
    outputDocument.remove(nameElements)

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
