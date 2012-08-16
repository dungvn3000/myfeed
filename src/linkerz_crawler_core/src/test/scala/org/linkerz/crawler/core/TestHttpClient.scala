/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core

import model.WebUrl
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import com.twitter.util.Time
import com.ning.http.client._

/**
 * The Class TestFinagle.
 *
 * @author Nguyen Duc Dung
 * @since 7/10/12, 1:03 AM
 *
 */

@RunWith(classOf[JUnitRunner])
class TestHttpClient extends FunSuite {

  test("testAsyncHttpClient") {
    val cf = new AsyncHttpClientConfig.Builder()
      .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:10.0.2) Gecko/20100101 Firefox/10.0.2")
      .setCompressionEnabled(true)
      .build()
    val asyncHttpClient = new AsyncHttpClient(cf)
    val f = asyncHttpClient.prepareGet("http://vnexpress.net/").execute()
    val time = Time.now
    val r = f.get()
    println(Time.now.inMilliseconds - time.inMilliseconds + "ms")
    r.getHeaders.values().toArray.foreach(println)
  }
}
