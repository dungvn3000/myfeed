/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core.util

import edu.uci.ics.crawler4j.url.URLCanonicalizer
import java.net.URI
import org.apache.http.client.utils.URIUtils
import org.junit.Test

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

  @Test
  def testParse() {
    val url1 = "http://vnexpress.net/gl/xa-hoi/giao-duc/2012/08/tu-choi-tuyen-thang-nu-sinh-thi-do-thu-khoa-bao-chi"
    val url2 = "http://vnexpress.net/gl/xa-hoi/giao-duc/2012/08/tu-choi-tuyen-thang-nu-sinh-thi-do-thu-khoa-bao-chi/"
    assert(UrlUtils.normalize(url1).equals(url2))

    val url3 = "http://search.vnexpress.net/news?s=%C4%91&g=66FD8EC7-FA76-4A7C-8FE3-DB605B4C9CE0&butS=yes"
    val url4 = "http://search.vnexpress.net/news?butS=yes&g=66FD8EC7-FA76-4A7C-8FE3-DB605B4C9CE0&s=%C4%91"

    assert(UrlUtils.normalize(url3).equals(url4))

    val url5 = "http://vnexpress.net/abc.jpg"
    val url6 = "http://vnexpress.net/abc.jpg"

    assert(UrlUtils.normalize(url5).equals(url6))

    val url7 = "http://vnexpress.net"
    val url8 = "http://vnexpress.net/"

    assert(UrlUtils.normalize(url7).equals(url8))

    val url9 = "http://abc.net//abcd/ddd"
    val url10 = "http://abc.net/abcd/ddd/"
    assert(UrlUtils.normalize(url9) == url10)

    val url11 = "http://vnexpress.net/gl/xa%2Dhoi/giao%2Dduc/2012/08/tu%2Dchoi%2Dtuyen%2Dthang%2Dnu%2Dsinh%2Dthi%2Ddo%2Dthu%2Dkhoa%2Dbao%2Dchi"
    val url12 = "http://vnexpress.net/gl/xa-hoi/giao-duc/2012/08/tu-choi-tuyen-thang-nu-sinh-thi-do-thu-khoa-bao-chi/"

    assert(UrlUtils.normalize(url11) == url12)
  }

}
