/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser

import org.linkerz.crawler.core.downloader.DownloadResult
import grizzled.slf4j.Logging


/**
 * The Class Parser.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:09 AM
 *
 */

trait Parser extends Logging {

  def parse(downloadResult: DownloadResult): ParserResult

}