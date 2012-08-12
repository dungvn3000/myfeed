/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.fetcher

import org.linkerz.crawler.core.downloader.Downloader
import org.linkerz.crawler.core.parser.{ParserResult, Parser}
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class Fetcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/1/12, 1:52 AM
 *
 */

class Fetcher(downloader: Downloader, parser: Parser) {

  /**
   * Fetch a url
   * @param webUrl
   */
  def fetch(webUrl: WebUrl): ParserResult = {
    val downloadResult = downloader.download(webUrl)
    val parserResult = parser.parse(downloadResult)
    parserResult
  }

}
