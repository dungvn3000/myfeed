/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.fetcher

import org.scalatest.FunSuite
import grizzled.slf4j.Logging
import org.linkerz.crawler.core.factory.{DefaultParserFactory, DefaultDownloadFactory}
import org.linkerz.crawler.core.job.CrawlJob

/**
 * The Class TestFetcher.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 9:08 PM
 *
 */

class TestFetcher extends FunSuite with Logging {

  test("testFetchVnExpress") {
    val downloadFactory = new DefaultDownloadFactory()
    val downloader = downloadFactory.createDownloader()
    val imageDownloader = downloadFactory.createImageDownloader()
    val parser = new DefaultParserFactory().createParser()
    val fetcher = new Fetcher(downloader, imageDownloader, parser)

    var time = System.currentTimeMillis()
    val result = fetcher.fetch(new CrawlJob("http://vnexpress.net/"))

    time = System.currentTimeMillis() - time

    println(time + " ms")

  }

}
