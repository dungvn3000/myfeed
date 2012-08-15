/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser

import org.scalatest.FunSuite
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import java.net.URI
import org.apache.http.client.utils.URIUtils
import org.linkerz.crawler.core.util.UrlUtils
import org.springframework.web.util.UriComponentsBuilder

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

    val domainName1 = URIUtils.extractHost(new URI(url1))
    val domainName2 = URIUtils.extractHost(new URI(url2))
    val domainName3 = URIUtils.extractHost(new URI(url3))

    assert(domainName1.getHostName == "vnexpress.net")
    assert(domainName2.getHostName == "vnexpress.net")
    assert(domainName3.getHostName == "search.vnexpress.net")
  }

  test("testURLCanonicalizer") {
    assert(URLCanonicalizer
      .getCanonicalURL("http://www.example.com/display?category=foo/bar+baz") ==
      "http://www.example.com/display?category=foo%2Fbar%2Bbaz")
  }

  test("testParse") {
    val url1 = "http://vnexpress.net/gl/xa-hoi/giao-duc/2012/08/tu-choi-tuyen-thang-nu-sinh-thi-do-thu-khoa-bao-chi"
    val url2 = "http://vnexpress.net/gl/xa-hoi/giao-duc/2012/08/tu-choi-tuyen-thang-nu-sinh-thi-do-thu-khoa-bao-chi/"
    assert(UrlUtils.normalize(url1).equals(url2))

    val url3 = "http://search.vnexpress.net/news?s=%C4%91&g=66FD8EC7-FA76-4A7C-8FE3-DB605B4C9CE0&butS=yes"
    val url4 = "http://search.vnexpress.net/news?s=%c4%91&g=66fd8ec7-fa76-4a7c-8fe3-db605b4c9ce0&buts=yes"

    assert(UrlUtils.normalize(url3).equals(url4))

    val url5 = "http://vnexpress.net/abc.jpg"
    val url6 = "http://vnexpress.net/abc.jpg"

    assert(UrlUtils.normalize(url5).equals(url6))

    val url7 = "http://vnexpress.net"
    val url8 = "http://vnexpress.net/"

    assert(UrlUtils.normalize(url7).equals(url8))
  }

}
