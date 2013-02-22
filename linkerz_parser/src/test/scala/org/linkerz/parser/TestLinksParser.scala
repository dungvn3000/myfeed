package org.linkerz.parser

import org.scalatest.FunSuite
import org.jsoup.Jsoup
import util.FileUtil._
import org.expecty.Expecty

/**
 * The Class TestLinksParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/27/12 6:40 PM
 *
 */
class TestLinksParser extends FunSuite {

  val expect = new Expecty()

  test("parse thanhnien1.html") {
    val url = "http://www.thanhnien.com.vn/pages/20121219/5-bong-hong-buon-ma-tuy-lanh-an.aspx"
    val doc = Jsoup.parse(getResourceAsStream("thanhnien1.html"), "utf-8", url)
    val parser = new LinksParser
    val links = parser.parse(doc)

    expect {
      links.size == 149
    }
  }

  test("parse ngoisao1.html") {
    val url = "http://ngoisao.net/tin-tuc/hollywood/2012/12/hoa-hau-hoan-vu-2011-gay-soc-voi-hinh-anh-xau-xi-224619/"
    val doc = Jsoup.parse(getResourceAsStream("ngoisao1.html"), "utf-8", url)
    val parser = new LinksParser
    val links = parser.parse(doc)

    expect {
      links.size == 105
    }
  }


}
