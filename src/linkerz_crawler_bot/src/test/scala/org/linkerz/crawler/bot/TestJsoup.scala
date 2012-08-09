/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot

import org.scalatest.FunSuite
import org.jsoup.Jsoup
import org.linkerz.test.spring.SpringContext
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.Link
import java.io.ByteArrayInputStream
import collection.JavaConversions._


/**
 * The Class TestJSoup.
 *
 * @author Nguyen Duc Dung
 * @since 8/9/12, 10:55 PM
 *
 */

class TestJSoup extends FunSuite with SpringContext {

  test("testJSoup1") {
    val doc = Jsoup.
      connect("http://localhost/vnexpress/vnexpress.net/gl/van-hoa/2012/08/trong-tan-anh-tho-nhan-quyet-dinh-canh-cao-1/index.html").get()
    val title = doc.select(".content .Title")
    val description = doc.select(".content .Lead")
    val content = doc.select(".content p")
    val img = doc.select(".content img")
    println(title.text())
    println(description.text())
    println(content.text())
    println(img.attr("src"))
  }

  test("testJSoup2") {
    val doc = Jsoup.connect("http://java.dzone.com/articles/thursday-code-puzzler-ray").get()
    val title = doc.select("#articleHead h1")
    val description = doc.select(".content p:eq(2)")
    val content = doc.select(".content p")
    val img = doc.select("img.dz_logo")
    println("Title: " + title.text())
    println("Description: " + description.text())
    println("Content: " + content.text())
    println("Img: " + img.attr("src"))
  }

}
