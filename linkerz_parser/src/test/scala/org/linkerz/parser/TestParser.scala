/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.parser

import org.jsoup.Jsoup
import util.FileUtil._
import org.scalatest.FunSuite


/**
 * The Class TestParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 10:58 PM
 *
 */
class TestParser extends FunSuite {

  test("parse thanhnien1.html") {
    val url = "http://www.thanhnien.com.vn/pages/20121219/5-bong-hong-buon-ma-tuy-lanh-an.aspx"
    val doc = Jsoup.parse(getResourceAsStream("thanhnien1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)
    assert(article.title.length == 33)
    assert(article.text.length == 1009)
    assert(article.images.size == 1)
  }

  test("parse ngoisao1.html") {
    val url = "http://ngoisao.net/tin-tuc/hollywood/2012/12/hoa-hau-hoan-vu-2011-gay-soc-voi-hinh-anh-xau-xi-224619/"
    val doc = Jsoup.parse(getResourceAsStream("ngoisao1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)
    assert(article.title.length == 48)
    assert(article.text.length == 1157)
    assert(article.images.size == 13)
  }

  test("parse tinhte1.html") {
    val url = "http://www.tinhte.vn/threads/1739393/"
    val doc = Jsoup.parse(getResourceAsStream("tinhte1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 42)
    assert(article.text.length == 1042)
    assert(article.images.size == 3)
  }

  test("parse 24h1.html") {
    val url = "http://hcm.24h.com.vn/giao-duc-du-hoc/dot-nhap-lo-luyen-thi-cong-chuc-han-quoc-c216a508182.html"
    val doc = Jsoup.parse(getResourceAsStream("24h1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 40)
    assert(article.text.length == 793)
    assert(article.images.size == 12)
  }

  test("parser vnexpress1.html") {
    val url = "http://vnexpress.net/gl/ban-doc-viet/tam-su/2012/12/vo-ngoai-tinh-sau-nhung-lan-bi-toi-danh-dap-da-man/"
    val doc = Jsoup.parse(getResourceAsStream("vnexpress1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 50)
    assert(article.text.length == 6804)
    assert(article.images.size == 0)
  }

  test("parse vnexpress2.html") {
    val url = "http://ione.vnexpress.net/tin-tuc/vui-la/anh/2012/12/41584-nhung-nguoi-kho-hanh-vi-bot-la.html"
    val doc = Jsoup.parse(getResourceAsStream("vnexpress2.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 30)
    assert(article.text.length == 2727)
    assert(article.images.size == 5)
  }

  test("parse genk1.html") {
    val url = "http://genk.vn/internet/amazon-ke-lao-luyen-day-chien-luoc-trong-cuoc-chien-cong-nghe-2012122104231130.chn"
    val doc = Jsoup.parse(getResourceAsStream("genk1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 62)
    assert(article.text.length == 19968)
    assert(article.images.size == 8)
  }

  test("parse zing1.html") {
    val url = "http://news.zing.vn/xa-hoi/moi-nga-duong-ve-nha-tho-tac-nghn-dem-giang-sinh/a293004.html#home_noibat1"
    val doc = Jsoup.parse(getResourceAsStream("zing1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 49)
    assert(article.text.length == 4150)
    assert(article.images.size == 21)
  }

  test("parse xinh1.html") {
    val url = "http://xinhxinh.com.vn/phong-cach/20121224150720142/4-buoc-mix-do-giup-ban-tu-tin-toa-sang-dem-noel.xinh"
    val doc = Jsoup.parse(getResourceAsStream("xinh1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 47)
    assert(article.text.length == 6359)
    assert(article.images.size == 46)
  }

  test("parse dantri1.html") {
    val url = "http://dantri.com.vn/su-kien/chot-lich-nghi-le-tet-2013-cua-cong-chuc-vien-chuc-677792.htm"
    val doc = Jsoup.parse(getResourceAsStream("dantri1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 51)
    assert(article.text.length == 2922)
    assert(article.images.size == 6)
  }

  test("parse dzone1.html") {
    val url = "http://java.dzone.com/articles/10-software-process-management"
    val doc = Jsoup.parse(getResourceAsStream("dzone1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 45)
    assert(article.text.length == 4659)
    assert(article.images.size == 1)
  }

  test("parse dzone2.html") {
    val url = "http://java.dzone.com/articles/4-most-important-skills"
    val doc = Jsoup.parse(getResourceAsStream("dzone2.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 52)
    assert(article.text.length == 8562)
    assert(article.images.size == 5)
  }

  test("parse eclipse1.html") {
    val url = "http://ekramalikazi.wordpress.com/eclipse/eclipse-settings/"
    val doc = Jsoup.parse(getResourceAsStream("eclipse1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 16)
    assert(article.text.length == 378)
    assert(article.images.size == 5)
  }

  test("parse scala1.html") {
    val url = "http://www.scala-lang.org/node/26632"
    val doc = Jsoup.parse(getResourceAsStream("scala1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 70)
    assert(article.text.length == 976)
    assert(article.images.size == 0)
  }

  test("parse bbc1.html") {
    val url = "http://www.bbc.co.uk/news/world-us-canada-20838925"
    val doc = Jsoup.parse(getResourceAsStream("bbc1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 55)
    assert(article.text.length == 4084)
    assert(article.images.size == 2)
  }

  test("parse cnn2.html") {
    val url = "http://edition.cnn.com/2010/POLITICS/08/13/democrats.social.security/index.html"
    val doc = Jsoup.parse(getResourceAsStream("cnn2.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 54)
    assert(article.text.length == 6337)
    assert(article.images.size == 2)
  }

  test("parse soha1.html") {
    val url = "http://soha.vn/giai-tri/can-lo-lo-la-loi-voi-khan-tam-20121225102525545.htm"
    val doc = Jsoup.parse(getResourceAsStream("soha1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 29)
    assert(article.text.length == 936)
    assert(article.images.size == 14)
  }

  test("parse teen9x1.html") {
    val url = "http://teen9x.net/bai-viet/bep-nuc/83-boy-vao-bep-che-banh-tinh-yeu-690.html"
    val doc = Jsoup.parse(getResourceAsStream("teen9x1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 33)
    assert(article.text.length == 1339)
    assert(article.images.size == 13)
  }

  test("parse nld1.html") {
    val url = "http://nld.com.vn/20121226051446379p0c1019/thieu-tien-hui-xach-dao-di-noi-chuyen-voi-chu-no.htm"
    val doc = Jsoup.parse(getResourceAsStream("nld1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)
    
    assert(article.title.length == 51)
    assert(article.text.length == 1218)
    assert(article.images.size == 1)
  }

  test("parse cnn1.html") {
    val url = "http://edition.cnn.com/2012/05/14/living/top-baby-names/index.html"
    val doc = Jsoup.parse(getResourceAsStream("cnn1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 60)
    assert(article.text.length == 2577)
    assert(article.images.size == 1)
  }

  test("parse empty.html") {
    val url = "localhost"
    val doc = Jsoup.parse(getResourceAsStream("empty.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)

    assert(article.title.length == 0)
    assert(article.text.length == 0)
    assert(article.images.size == 0)
  }

  test("parse soha.vn home page") {
    val url = "http://soha.vn/"
    val doc = Jsoup.parse(getResourceAsStream("soha.homepage.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)
  }

  test("parse vnexpress.net home page") {
    val url = "http://vnexpress.net/"
    val doc = Jsoup.parse(getResourceAsStream("vnexpress.hompage.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)
  }

  test("parse thanhnien.com.vn home page") {
    val url = "http://www.thanhnien.com.vn/"
    val doc = Jsoup.parse(getResourceAsStream("thanhnien.homepage.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc)
  }
}
