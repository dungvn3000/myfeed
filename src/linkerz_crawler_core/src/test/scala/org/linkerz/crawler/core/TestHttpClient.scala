/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import com.twitter.util.Time
import org.apache.http.impl.client.{BasicResponseHandler, DefaultHttpClient}
import org.apache.http.client.methods.HttpGet
import org.apache.http.params.{HttpProtocolParamBean, BasicHttpParams, CoreProtocolPNames}
import org.apache.http.HttpVersion
import dispatch.{:/, Http}
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

  test("testSimpleDownload") {

    val params = new BasicHttpParams()
    val paramsBean = new HttpProtocolParamBean(params)
    paramsBean.setVersion(HttpVersion.HTTP_1_1)
    paramsBean.setContentCharset("UTF-8")
    paramsBean.setUseExpectContinue(false)

    params.setParameter(CoreProtocolPNames.USER_AGENT,
      "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:10.0.2) Gecko/20100101 Firefox/10.0.2")

    params.setBooleanParameter("http.protocol.handle-redirects", false)

    val httpClient = new DefaultHttpClient()
    httpClient.setParams(params)

    val httpGet = new HttpGet("http://vnexpress.net/")


    val responseHandler = new BasicResponseHandler()
    val time = Time.now
    val responseBody = httpClient.execute(httpGet, responseHandler)
    println(Time.now.inMilliseconds - time.inMilliseconds + "ms")

    //    println(responseBody.toString())
  }

  test("testDispatch") {
    val http = new Http
    val request = :/("vnexpress.net")
//    val handler = request >>> System.out
//    http(handler)
  }

  test("testAsyncHttpClient") {
    val cf = new AsyncHttpClientConfig.Builder()
    cf.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:10.0.2) Gecko/20100101 Firefox/10.0.2")
    val asyncHttpClient = new AsyncHttpClient(cf.build())
    val f = asyncHttpClient.prepareGet("http://vnexpress.net").execute(new AsyncCompletionHandler[Response] {
      def onCompleted(response: Response) = {
        println(response.getStatusCode)
        response
      }
    })
    val time = Time.now
    val r = f.get()
    println(Time.now.inMilliseconds - time.inMilliseconds + "ms")
  }
}
