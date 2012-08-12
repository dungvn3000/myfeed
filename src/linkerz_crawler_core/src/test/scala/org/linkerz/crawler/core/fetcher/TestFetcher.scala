/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.fetcher

import org.scalatest.FunSuite
import grizzled.slf4j.Logging
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.crawler.core.downloader.DefaultDownload
import org.linkerz.crawler.core.parser.DefaultParser

/**
 * The Class TestFetcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 9:08 PM
 *
 */

class TestFetcher extends FunSuite with Logging {

  test("testFetchVnExpress") {
    val fetcher = new Fetcher(new DefaultDownload, new DefaultParser)

    var time = System.currentTimeMillis()
    val result = fetcher.fetch(new WebUrl("http://localhost/vnexpress/vnexpress.net/"))

    time = System.currentTimeMillis() - time

    println(time + " ms")

  }

}
