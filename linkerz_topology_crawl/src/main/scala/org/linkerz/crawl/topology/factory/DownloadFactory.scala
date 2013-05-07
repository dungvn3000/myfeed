/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.factory

import org.linkerz.crawl.topology.downloader.{AsyncDownloader, DefaultDownloader}
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.scheme.{SchemeRegistry, PlainSocketFactory, Scheme}
import org.apache.http.params.{BasicHttpParams, CoreProtocolPNames, CoreConnectionPNames}
import org.linkerz.crawl.topology.downloader.handler.StrictlyRedirectStrategy
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.conn.PoolingClientConnectionManager
import org.apache.http.client.params.CookiePolicy
import com.ning.http.client.{AsyncHttpClient, AsyncHttpClientConfig}

/**
 * The Class DefaultDownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:41 PM
 *
 */

object DownloadFactory {

  def createDownloaderWithHttpClient() = {
    val httpParams = new BasicHttpParams()
    httpParams.setParameter(CoreProtocolPNames.USER_AGENT, FireFoxUserAgent.value)
    //Set time out 10s
    httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 10)
    httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 10)
    httpParams.setParameter(CoreConnectionPNames.TCP_NODELAY, true)
    httpParams.setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false)
    httpParams.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8")
    httpParams.setParameter("http.protocol.cookie-policy", CookiePolicy.IGNORE_COOKIES)
    httpParams.setParameter("Cache-Control", "max-age=0")

    val schemeRegistry = new SchemeRegistry
    schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory))
    schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory))
    val cm = new PoolingClientConnectionManager(schemeRegistry)
    cm.setMaxTotal(20000)
    cm.setDefaultMaxPerRoute(500)

    val client = new DefaultHttpClient(cm, httpParams)
    client.setRedirectStrategy(new StrictlyRedirectStrategy)
    new DefaultDownloader(client)
  }

  def createDownloaderWithAsyncHttpClient() = {
    val builder = new AsyncHttpClientConfig.Builder()
      .setUserAgent(FireFoxUserAgent.value)
      .setCompressionEnabled(true)
      .setAllowPoolingConnection(true)
      .setFollowRedirects(true)
      .setMaximumConnectionsPerHost(100)
      .setMaximumNumberOfRedirects(1000)
      .setMaximumConnectionsTotal(1000)
      .setMaxRequestRetry(10)
      .build()

    new AsyncDownloader(new AsyncHttpClient(builder))
  }
}