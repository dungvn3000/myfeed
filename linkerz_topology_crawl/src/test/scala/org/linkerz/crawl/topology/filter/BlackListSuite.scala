package org.linkerz.crawl.topology.filter

import org.scalatest.FunSuite

/**
 * The Class BlackListSuite.
 *
 * @author Nguyen Duc Dung
 * @since 2/12/13 11:33 AM
 *
 */
class BlackListSuite extends FunSuite {

  test("test black list partern") {
    val urls = List(
      "http://news.zing.vn/feedback",
      "http://news.zing.vn/privacy.html",
      "http://www.thanhnien.com.vn/Pages/Lien-he-quang-cao.aspx",
      "http://www.thanhnien.com.vn/Pages/Gop-y.aspx",
      "http://www.bbc.co.uk/feedback/"
    )

    urls.foreach(url => {
      assert(BlackUrlPattern.matches(url))
    })
  }

}
