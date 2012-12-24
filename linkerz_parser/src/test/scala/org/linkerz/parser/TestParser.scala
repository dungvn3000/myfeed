/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.parser

import grizzled.slf4j.Logging
import org.junit.Test
import org.jsoup.Jsoup


/**
 * The Class TestParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 10:58 PM
 *
 */
class TestParser extends Logging {

  @Test
  def testParser1() {
    val url = "http://www.thanhnien.com.vn/pages/20121219/5-bong-hong-buon-ma-tuy-lanh-an.aspx"
    val doc = Jsoup.connect(url).get()
    val parser = new ArticleParser
    parser.parse(doc)
  }

  @Test
  def testParser2() {
    val url = "http://ngoisao.net/tin-tuc/hollywood/2012/12/hoa-hau-hoan-vu-2011-gay-soc-voi-hinh-anh-xau-xi-224619/"
    val doc = Jsoup.connect(url).get()
    val parser = new ArticleParser
    parser.parse(doc)
  }

  @Test
  def testParser3() {
    val url = "http://www.tinhte.vn/threads/1739393/"
    val doc = Jsoup.connect(url).get()
    val parser = new ArticleParser
    parser.parse(doc)
  }

 @Test
  def testParser4() {
    val url = "http://hcm.24h.com.vn/giao-duc-du-hoc/dot-nhap-lo-luyen-thi-cong-chuc-han-quoc-c216a508182.html"
    val doc = Jsoup.connect(url).get()
    val parser = new ArticleParser
    parser.parse(doc)
  }

  @Test
  def testParser5() {
    val url = "http://vnexpress.net/gl/ban-doc-viet/tam-su/2012/12/vo-ngoai-tinh-sau-nhung-lan-bi-toi-danh-dap-da-man/"
    val doc = Jsoup.connect(url).get()
    val parser = new ArticleParser
    parser.parse(doc)
  }

  @Test
  def testParser6() {
    val url = "http://ione.vnexpress.net/tin-tuc/vui-la/anh/2012/12/41584-nhung-nguoi-kho-hanh-vi-bot-la.html"
    val doc = Jsoup.connect(url).get()
    val parser = new ArticleParser
    parser.parse(doc)
  }

  @Test
  def testParser7() {
    val url = "http://genk.vn/internet/amazon-ke-lao-luyen-day-chien-luoc-trong-cuoc-chien-cong-nghe-2012122104231130.chn"
    val doc = Jsoup.connect(url).get()
    val parser = new ArticleParser
    parser.parse(doc)
  }

}
