/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core

import com.ning.http.client._
import net.coobird.thumbnailator.Thumbnails
import org.junit.experimental.categories.Category
import org.junit.Test
import org.scalatest.FunSuite

/**
 * The Class TestFinagle.
 *
 * @author Nguyen Duc Dung
 * @since 7/10/12, 1:03 AM
 *
 */
class HttpClientSuite extends FunSuite {

  val cf = new AsyncHttpClientConfig.Builder()
    .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:10.0.2) Gecko/20100101 Firefox/10.0.2")
    .setCompressionEnabled(true)
    .build()

  test("test connect to vnexpress") {
    val asyncHttpClient = new AsyncHttpClient(cf)
    val f = asyncHttpClient.prepareGet("http://vnexpress.net/").execute()
    val time = System.currentTimeMillis()
    val r = f.get()
    println(System.currentTimeMillis() - time + "ms")
    r.getHeaders.values().toArray.foreach(println)
  }

  test("test download an image and resize") {
    val asyncHttpClient = new AsyncHttpClient(cf)
    val time = System.currentTimeMillis()
    val response = asyncHttpClient
      .prepareGet("http://vnexpress.net/Files/Subject/3b/bd/b0/a4/ba_Huong.jpg").execute().get()
    println("response = " + response.getStatusCode)
    val img = Thumbnails.of(response.getResponseBodyAsStream).forceSize(80, 80)
      .asBufferedImage()

    println(System.currentTimeMillis - time + "ms")
    assert(img.getWidth == 80)
    assert(img.getHeight == 80)
  }

}