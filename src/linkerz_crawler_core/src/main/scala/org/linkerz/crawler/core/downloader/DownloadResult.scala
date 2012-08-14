/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.downloader

import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class DownloadResult.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:10 AM
 *
 */

case class DownloadResult(webUrl: WebUrl, byteContent: Array[Byte]) {

  var responseCode: Int = _

}
