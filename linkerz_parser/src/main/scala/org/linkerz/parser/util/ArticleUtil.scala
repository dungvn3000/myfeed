package org.linkerz.parser.util

import org.jsoup.parser.Tag

/**
 * The Class ArticleUtil.
 *
 * @author Nguyen Duc Dung
 * @since 12/23/12 4:57 AM
 *
 */
object ArticleUtil {

  def isArticleContentTag(tag: Tag) = tag.getName != "title" &&
    tag.getName != "head" &&
    tag.getName != "meta" &&
    tag.getName != "script" &&
    tag.getName != "style" &&
    tag.getName != "link" &&
    tag.getName != "iframe" &&
    tag.getName != "select" &&
    tag.getName != "option" &&
    tag.getName != "noscript"

  def removeNonAlphabetsCharacter(st: String) = st.replaceAll("[^a-zA-Z0-9]","")

  /**
   * Checking whether the title is contain the text or not, by skip non alphabet character.
   * @param title
   * @param text
   * @return
   */
  def titleContain(title: String, text: String) = {
    val cleanTitle = removeNonAlphabetsCharacter(title.toLowerCase)
    val cleanText = removeNonAlphabetsCharacter(text.toLowerCase)
    cleanTitle.contains(cleanText)
  }

}
