/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext
import parser.LinkerZParser
import plugin.VnExpressPlugin
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.Link
import collection.JavaConversions._
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.crawler.core.model.WebUrl

/**
 * The Class TestLinkerZParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 4:34 PM
 *
 */

class TestLinkerZParser extends FunSuite with SpringContext {

  test("testInstallPlugin") {
    val linkerZParser = context.getBean("linkerZParser", classOf[LinkerZParser])
    assert(linkerZParser.install(classOf[VnExpressPlugin].getName))
    linkerZParser.delete(classOf[VnExpressPlugin].getName)
  }

  test("testParser") {
    //Install the plugin first
    val linkerZParser = context.getBean("linkerZParser", classOf[LinkerZParser])
    assert(linkerZParser.install(classOf[VnExpressPlugin].getName))

    //Reload the list
    linkerZParser.load()

    val fetcher = new Fetcher
    val result = fetcher.fetch(new WebUrl("http://vnexpress.net/gl/phap-luat/2012/08/dai-gia-dat-cang-bi-dieu-tra-lua-dao-1-000-ty-dong/"))
    val link = result.webPage.asLink()

    linkerZParser.parse(link)

    println("link = " + link.url)
    println("title = " + link.title)
    println("description = " + link.description)
    println("img = " + link.featureImageUrl)

    //Delete the plugin
    linkerZParser.delete(classOf[VnExpressPlugin].getName)
  }

}