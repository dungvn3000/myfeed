/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.fetcher

import org.linkerz.crawler.core.downloader.Downloader
import org.linkerz.crawler.core.parser.{ParserResult, Parser}
import org.linkerz.crawler.core.model.{WebPage, WebUrl}

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

    var parserResult: ParserResult = null
    if (downloadResult.responseCode == 200) {
      //Only parse when the response code is ok
      parserResult = parser.parse(downloadResult)
    } else {
      //TODO: Find the way how to treat the error url
      val webPage = new WebPage
      webPage.webUrl = downloadResult.webUrl
      webPage.content = downloadResult.byteContent
      parserResult = new ParserResult(webPage)
    }

    //Set response for sever to model
    parserResult.webPage.responseCode = downloadResult.responseCode

    parserResult
  }

}
