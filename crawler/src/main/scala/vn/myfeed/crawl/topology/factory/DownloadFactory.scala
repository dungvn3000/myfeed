/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package vn.myfeed.crawl.topology.factory

import vn.myfeed.crawl.topology.downloader.DefaultDownloader
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.scheme.{SchemeRegistry, PlainSocketFactory, Scheme}
import org.apache.http.params.{BasicHttpParams, CoreProtocolPNames, CoreConnectionPNames}
import vn.myfeed.crawl.topology.downloader.handler.{ResponseInterceptor, StrictlyRedirectStrategy}
import org.apache.http.impl.client.{DefaultHttpRequestRetryHandler, AbstractHttpClient, DefaultHttpClient}
import org.apache.http.impl.conn.PoolingClientConnectionManager
import org.apache.http.client.params.CookiePolicy

/**
 * The Class DefaultDownloadFactory.
 *
 * @author Nguyen Duc Dung
 * @since 8/13/12, 6:41 PM
 *
 */

object DownloadFactory {

  def createDownloader() = {
    val httpParams = new BasicHttpParams()
    httpParams.setParameter(CoreProtocolPNames.USER_AGENT, FireFoxUserAgent.value)
    //Set time out 30s
    httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 30)
    httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 30)
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
    client.asInstanceOf[AbstractHttpClient].setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
    client.getParams.setParameter("http.conn-manager.timeout", 120000L)
    client.getParams.setParameter("http.protocol.wait-for-continue", 10000L)
    client.addResponseInterceptor(new ResponseInterceptor)
    new DefaultDownloader(client)
  }
}