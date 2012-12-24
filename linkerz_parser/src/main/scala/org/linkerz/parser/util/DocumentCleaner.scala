package org.linkerz.parser.util

import org.jsoup.nodes.Document

/**
 * The Class DocumentCleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/25/12 1:02 AM
 *
 */
object DocumentCleaner {

  def clean(doc: Document) = {
    val cleanedHtml = doc.html.replaceAll("&nbsp;","")
    doc.html(cleanedHtml)
    doc.normalise()
  }

}
