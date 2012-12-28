package org.linkerz.parser.processor

import org.linkerz.parser.model.Article

/**
 * The Class DocumentCleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/25/12 2:08 PM
 *
 */
class DocumentCleaner extends Processor {
  def process(implicit article: Article) {
    val containerElement = article.containerElement
    val cleanedHtml = containerElement.html.replaceAll("&nbsp;"," ")
    containerElement.html(cleanedHtml)

    //Remove noscript tag
    val noScriptElement = containerElement.select("noscript")
    noScriptElement.remove()

    val scriptElement = containerElement.select("script")
    scriptElement.remove()

    val iframeElement = containerElement.select("iframe")
    iframeElement.remove()
  }
}
