/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.parser

import grizzled.slf4j.Logging
import org.junit.Test
import org.linkerz.crawl.topology.downloader.DefaultDownload
import org.linkerz.crawl.topology.job.CrawlJob

/**
 * The Class TestParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 10:58 PM
 *
 */
class TestParser extends Logging {

  @Test
  def testHtmlParser() {


    val downloader = new DefaultDownload
    val parser = new AutoDetectContentBlockParser
    val job = new CrawlJob("http://www.tinhte.vn/threads/1750793/")

    downloader.download(job)
    parser.parse(job)


  }

}
