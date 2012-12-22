package org.linkerz.crawl.topology.parser.filter

import org.linkerz.crawl.topology.parser.core.TextElement

/**
 * The Class Filter.
 *
 * @author Nguyen Duc Dung
 * @since 12/22/12 5:56 PM
 *
 */
trait Filter {

  def filter(textBlock: List[TextElement])

}
