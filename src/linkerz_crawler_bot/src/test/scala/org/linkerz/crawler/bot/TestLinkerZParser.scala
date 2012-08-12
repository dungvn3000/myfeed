/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot

import factory.ParserFactory
import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext
import plugin.vnexpress.VnExpressPlugin
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.crawler.core.model.WebUrl
import org.linkerz.crawler.core.downloader.DefaultDownload

/**
 * The Class TestLinkerZParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 4:34 PM
 *
 */

class TestLinkerZParser extends FunSuite with SpringContext {

  test("testParser") {
    //Install the plugin first
    val parserFactory = context.getBean("parserFactory", classOf[ParserFactory])
    parserFactory.install(classOf[VnExpressPlugin].getName)

    val fetcher = new Fetcher(new DefaultDownload, parserFactory.createParser)
    val result = fetcher.fetch(new WebUrl("http://vnexpress.net/gl/phap-luat/2012/08/dai-gia-dat-cang-bi-dieu-tra-lua-dao-1-000-ty-dong/"))
    val link = result.webPage.asLink()
    println("link = " + link.url)
    println("title = " + link.title)
    println("description = " + link.description)
    println("img = " + link.featureImageUrl)
  }
}
