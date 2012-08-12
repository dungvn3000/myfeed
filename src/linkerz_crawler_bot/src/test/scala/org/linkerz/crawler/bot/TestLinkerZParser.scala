/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext
import parser.LinkerZParser
import plugin.vnexpress.VnExpressPlugin
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.crawler.core.downloader.DefaultDownload
import org.linkerz.crawler.core.parser.DefaultParser

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

    val fetcher = new Fetcher(new DefaultDownload, new DefaultParser)
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

  test("testGetParser") {
    val linkerZParser = context.getBean("linkerZParser", classOf[LinkerZParser])
    assert(linkerZParser.get(classOf[VnExpressPlugin].getName) != null)
  }
}
