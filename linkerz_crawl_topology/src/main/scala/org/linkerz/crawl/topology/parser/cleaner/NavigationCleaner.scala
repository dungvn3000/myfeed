package org.linkerz.crawl.topology.parser.cleaner

import java.util.regex.Pattern

/**
 * The Class NavigationCleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 1:42 AM
 * 
 */
object NavigationCleaner extends Cleaner {
  def pattern = Pattern.compile("breadcrumb|crumbs|.*navlink.*|pagenav.*|pager|phantrang|.*menu.*",Pattern.CASE_INSENSITIVE)

}
