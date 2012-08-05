/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser

import org.scalatest.FunSuite
import com.googlecode.flaxcrawler.utils.UrlUtils

/**
 * The Class TestUrlUtil.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 7:58 PM
 *
 */

class TestUrlUtil extends FunSuite {

  test("testUrlUtil") {

    val url1 = "http://vnexpress.net/"
    val url2 = "http://vnexpress.net/gl/xa-hoi/giao-duc/2012/08/tu-choi-tuyen-thang-nu-sinh-thi-do-thu-khoa-bao-chi/"
    val url3 = "http://search.vnexpress.net/news?s=%C4%91&g=66FD8EC7-FA76-4A7C-8FE3-DB605B4C9CE0&butS=yes"

    val domainName1 = UrlUtils.getDomainName(url1)
    val domainName2 = UrlUtils.getDomainName(url2)
    val domainName3 = UrlUtils.getDomainName(url3)

    assert(domainName1 == "vnexpress.net")
    assert(domainName2 == "vnexpress.net")
    assert(domainName3 == "search.vnexpress.net")
  }

}
