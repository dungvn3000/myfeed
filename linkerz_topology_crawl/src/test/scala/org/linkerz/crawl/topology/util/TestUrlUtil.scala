/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.util

import edu.uci.ics.crawler4j.url.URLCanonicalizer
import java.net.URI
import org.apache.http.client.utils.URIUtils

/**
 * The Class TestUrlUtil.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 7:58 PM
 *
 */
class TestUrlUtil {

  @Test
  def testUrlUtil() {

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

  @Test
  def testURLCanonicalizer() {
    assert(URLCanonicalizer
      .getCanonicalURL("http://www.example.com/display?category=foo/bar+baz") ==
      "http://www.example.com/display?category=foo%2Fbar%2Bbaz")

    val url1 = "http://abc.net/"
    val url2 = "./abc/bcd"

    val url3 = URLCanonicalizer.getCanonicalURL(url2, url1)
    assert(url3 == "http://abc.net/abc/bcd")

    val url4 = "http://abc.net//abc/bcd"
    val url5 = URLCanonicalizer.getCanonicalURL(url4)

    assert(url5 == "http://abc.net/abc/bcd")

    val url6 = "http://abc.net/"
    val url7 = "http://abc.net/abc/bcd"

    val url8 = URLCanonicalizer.getCanonicalURL(url7, url6)

    assert(url8 == "http://abc.net/abc/bcd")
  }

}
