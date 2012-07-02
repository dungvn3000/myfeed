package org.linkerz.crawler.core.parser

import org.linkerz.crawler.core.download.DownloadResult

/**
 * The Class Parser.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 11:48 PM
 *
 */

trait Parser {

  /**
   * Parse a download result.
   * @param downloadResult
   * @return
   */
  def parse(downloadResult : DownloadResult) : ParserResult

}
