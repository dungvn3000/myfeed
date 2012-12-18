/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.crawl.topology.downloader.{ImageDownloader, DefaultDownload}
import org.apache.http.params.{CoreConnectionPNames, CoreProtocolPNames, BasicHttpParams}
import org.apache.http.impl.client.DefaultHttpClient

/**
 * The Class DefaultDownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:41 PM
 *
 */

object DownloadFactory {

  val httpParams = new BasicHttpParams()
  httpParams.setParameter(CoreProtocolPNames.USER_AGENT,
    "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
  //Set time out 30s
  httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 30)

  def createDownloader() = new DefaultDownload(new DefaultHttpClient(httpParams))

  def createImageDownloader() = new ImageDownloader(new DefaultHttpClient(httpParams))

}