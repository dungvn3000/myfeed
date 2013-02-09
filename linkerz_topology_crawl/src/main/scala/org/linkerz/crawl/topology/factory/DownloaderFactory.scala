/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.crawl.topology.downloader.{DefaultDownloader, WebPageDownloader}
import org.apache.http.params.{CoreConnectionPNames, CoreProtocolPNames, BasicHttpParams}
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.conn.scheme.{PlainSocketFactory, Scheme, SchemeRegistry}
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager

/**
 * The Class DefaultDownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:41 PM
 *
 */

object DownloaderFactory {

  val httpParams = new BasicHttpParams()
  httpParams.setParameter(CoreProtocolPNames.USER_AGENT,
    "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")
  //Set time out 30s
  httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 30)

  val schemeRegistry: SchemeRegistry = new SchemeRegistry
  schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory))
  schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory))
  val cm = new ThreadSafeClientConnManager(schemeRegistry)
  cm.setMaxTotal(100)
  cm.setDefaultMaxPerRoute(10)

  def createDefaultDownloader() = {
    val client = new DefaultHttpClient(cm, httpParams)
    client.setRedirectStrategy(new StrictlyRedirectStrategy)
    new DefaultDownloader(client)
  }

  def createWebPageDownloader() = {
    val client = new DefaultHttpClient(cm, httpParams)
    client.setRedirectStrategy(new StrictlyRedirectStrategy)
    new WebPageDownloader()(client)
  }
}