package org.linkerz.crawl.topology.parser.cleaner

import java.util.regex.Pattern

/**
 * The Class CommentCleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/19/12, 11:58 PM
 *
 */
object CommentCleaner extends Cleaner {

  override def pattern = Pattern.compile(".*comment.*|user*|.*avatar.*|member.*", Pattern.CASE_INSENSITIVE)

}
