package org.linkerz.crawl.topology.parser.cleaner

import java.util.regex.Pattern
import net.htmlparser.jericho.{OutputDocument, Source}

/**
 * The Class IFrameCleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 1:49 AM
 *
 */
object IFrameCleaner extends Cleaner {
  def pattern = Pattern.compile(".*facebook.*|.*twitter.*|.*google.*", Pattern.CASE_INSENSITIVE)

  override def clean(source: Source) = {
    val iframeElements = source.getAllElements("src", pattern)
    val outputDocument = new OutputDocument(source)
    outputDocument.remove(iframeElements)

    new Source(outputDocument.getSourceText)
  }
}
