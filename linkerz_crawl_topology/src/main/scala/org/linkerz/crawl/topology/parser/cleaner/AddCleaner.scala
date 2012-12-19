package org.linkerz.crawl.topology.parser.cleaner

import java.util.regex.Pattern

/**
 * The Class AddCleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 1:59 AM
 * 
 */
object AddCleaner extends Cleaner {
  def pattern = Pattern.compile(".*banner.*|.*quangcao.*", Pattern.CASE_INSENSITIVE)
}
