package org.linkerz.parser

import org.scalatest.FunSuite
import org.jsoup.Jsoup
import util.FileUtil._

/**
 * The Class TestContentSelectionParser.
 *
 * @author Nguyen Duc Dung
 * @since 12/28/12 2:25 PM
 *
 */
class TestArticleParserManualMode extends FunSuite {

  test("parse thanhnien1.html") {
    val url = "http://www.thanhnien.com.vn/pages/20121219/5-bong-hong-buon-ma-tuy-lanh-an.aspx"
    val doc = Jsoup.parse(getResourceAsStream("thanhnien1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc, ".article-content").get

    expect("5 \"bóng hồng\" buôn ma túy lãnh án")(article.title)
    assert(article.text.contains("(TNO) TAND TP.Đà Nẵng ngày 19.12 xét xử sơ thẩm"))
    assert(article.text.contains("Tin, ảnh: Vũ Phương Thảo"))
    expect("/Pictures201212/THANHLUAN/3/matuy.jpg;pvf9a951bcf19c254c")(article.images.head.src)
  }

  test("parser vnexpress1.html") {
    val url = "http://vnexpress.net/gl/ban-doc-viet/tam-su/2012/12/vo-ngoai-tinh-sau-nhung-lan-bi-toi-danh-dap-da-man/"
    val doc = Jsoup.parse(getResourceAsStream("vnexpress1.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc, ".cxtLeft").get

    expect("Vợ ngoại tình sau những lần bị tôi đánh đập dã man")(article.title)
    assert(article.text.contains("Vợ ngoại tình sau những lần bị tôi đánh đập dã man"))
    assert(article.text.contains("Trung"))
    expect(0)(article.images.size)
  }

  test("parser vnexpress3.html") {
    val url = "http://vnexpress.net/gl/ban-doc-viet/anh/2012/12/dong-nuoc-doc-tren-canh-dong-ha-noi/"
    val doc = Jsoup.parse(getResourceAsStream("vnexpress3.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc, ".cxtLeft").get

    expect("Dòng nước độc trên cánh đồng Hà Nội")(article.title)
    assert(article.text.contains("Dòng nước bốc lên lớp hơi bụi trắng khiến cây"))
    assert(article.text.contains("Nguyễn Đình Quân"))
    expect(2)(article.images.size)
  }

  test("parse vnexpress.net home page") {
    val url = "http://vnexpress.net/"
    val doc = Jsoup.parse(getResourceAsStream("vnexpress.hompage.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc, ".cxtLeft")

    expect(None)(article)
  }

  test("parse thanhnien.com.vn home page") {
    val url = "http://www.thanhnien.com.vn/"
    val doc = Jsoup.parse(getResourceAsStream("thanhnien.homepage.html"), "utf-8", url)
    val parser = new ArticleParser
    val article = parser.parse(doc, ".article-content")

    expect(None)(article)
  }
}
