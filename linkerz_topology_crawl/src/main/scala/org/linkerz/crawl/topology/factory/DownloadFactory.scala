/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.crawl.topology.downloader.DefaultDownloader
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.scheme.{SchemeRegistry, PlainSocketFactory, Scheme}
import org.apache.http.params.{BasicHttpParams, CoreProtocolPNames, CoreConnectionPNames}
import org.linkerz.crawl.topology.downloader.handler.StrictlyRedirectStrategy
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
  httpParams.setParameter(CoreProtocolPNames.USER_AGENT, FireFoxUserAgent.value)
  //Set time out 30s
  httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 30)

  val schemeRegistry: SchemeRegistry = new SchemeRegistry
  schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory))
  schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory))
  val cm = new ThreadSafeClientConnManager(schemeRegistry)
  cm.setMaxTotal(20000)
  cm.setDefaultMaxPerRoute(500)

  def createDownloader() = {
    val client = new DefaultHttpClient(cm, httpParams)
    client.setRedirectStrategy(new StrictlyRedirectStrategy)
    new DefaultDownloader(client)
  }
}