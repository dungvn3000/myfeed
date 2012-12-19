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
trait Cleaner {

  def pattern: Pattern

  def clean(source: Source) = {
    val classElements = source.getAllElements("class", pattern)
    val idElements = source.getAllElements("id", pattern)
    val nameElements = source.getAllElements("name", pattern)

    val outputDocument = new OutputDocument(source)
    outputDocument.remove(classElements)
    outputDocument.remove(idElements)
    outputDocument.remove(nameElements)

    new Source(outputDocument.getSourceText)
  }

}
