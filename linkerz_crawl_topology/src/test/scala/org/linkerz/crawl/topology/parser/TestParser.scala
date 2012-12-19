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


    val urls = List(
      "http://vnexpress.net/gl/khoa-hoc/2012/11/nguoi-mo-dau-the-he-vang-toan-hoc-viet-nam/",
      "http://www.tinhte.vn/threads/1750793/",
      "http://www.thanhnien.com.vn/pages/20121219/5-bong-hong-buon-ma-tuy-lanh-an.aspx",
      "http://cafef.vn/kinh-te-vi-mo-dau-tu/chay-cong-chuc-100-trieu-nganh-noi-vu-cung-giat-minh-20121219074522405ca33.chn",
      "http://2sao.vn/p0c1000n20121218185701929/xem-them-anh-con-gai-yeu-cua-linh-nga.vnn",
      "http://afamily.vn/dep/trang-diem-bien-hoa-cuc-nhanh-tu-cong-so-toi-tiec-toi-20121219084749754.chn"
    )

    urls.foreach(url => {
      val downloader = new DefaultDownload
      val parser = new AutoDetectContentBlockParser
      val job = new CrawlJob(url)
      downloader.download(job)
      parser.parse(job)
    })
  }

}
