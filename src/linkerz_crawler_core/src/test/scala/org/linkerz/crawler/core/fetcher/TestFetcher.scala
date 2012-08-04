/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.fetcher

import org.scalatest.FunSuite
import grizzled.slf4j.Logging
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class TestFetcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 9:08 PM
 *
 */

class TestFetcher extends FunSuite with Logging {

  test("testFetchVnExpress") {
    val fetcher = new Fetcher

    var time = System.currentTimeMillis()
    val result = fetcher.fetch(new WebUrl("http://localhost/vnexpress/vnexpress.net/"))

    time = System.currentTimeMillis() - time

    println(time + " ms")

    println(result.webPage.subtitle)
    assert(result.webPage.language == "vi")
  }

}
