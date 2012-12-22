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

}
