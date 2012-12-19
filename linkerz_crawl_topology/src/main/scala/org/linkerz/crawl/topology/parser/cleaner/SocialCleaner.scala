package org.linkerz.crawl.topology.parser.cleaner

import java.util.regex.Pattern

/**
 * The Class SocialCleaner.
 *
 * @author Nguyen Duc Dung
 * @since 12/20/12, 1:24 AM
 * 
 */
object SocialCleaner extends Cleaner {

  override def pattern = Pattern.compile(".*like.*|.*vote.*|.*share.*|.*facebook.*|.*twitter.*|.*google.*|linkhay|sociable",
    Pattern.CASE_INSENSITIVE)


}
