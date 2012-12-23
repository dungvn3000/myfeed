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
    tag.isBlock

}
