/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.testdata

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.NewFeed
import collection.JavaConverters._
import org.linkerz.crawler.bot.plugin.zing.ZingPlugin
import org.linkerz.crawler.bot.factory.ParserPluginFactory
import org.linkerz.crawler.bot.plugin.vnexpress.VnExpressPlugin
import org.linkerz.test.categories.ManualTest
import org.junit.experimental.categories.Category
import org.junit.Test

/**
 * The Class TestData.
 *
 * @author Nguyen Duc Dung
 * @since 8/18/12, 5:22 AM
 *
 */
@Category(Array(classOf[ManualTest]))
class TestData extends SpringContext {

  val mongoOperations = context.getBean("mongoTemplate", classOf[MongoOperations])
  val parserFactory = context.getBean("parserFactory", classOf[ParserPluginFactory])

  @Test
  def testAddVNExpress() {
    val vnExpressFeed = new NewFeed
    vnExpressFeed.name = "VnExpress.net"
    vnExpressFeed.group = "vnexpress.net"
    vnExpressFeed.time = 15
    vnExpressFeed.url = "http://vnexpress.net/"
    vnExpressFeed.urlRegex = List("*/vnexpress.net/*/*/2012/*").asJava
    vnExpressFeed.excludeUrl = List(
      "*/vnexpress.net/*/cuoi/*",
      "*/vnexpress.net/gl/ban-doc-viet/tam-su/*").asJava
    vnExpressFeed.enable = true
    mongoOperations.save(vnExpressFeed)
    assert(vnExpressFeed.id != null)

    parserFactory.install(classOf[VnExpressPlugin].getName)
  }

  @Test
  def testAddZing() {
    val zingFeed = new NewFeed
    zingFeed.name = "Zing News"
    zingFeed.group = "zing.vn"
    zingFeed.time = 15
    zingFeed.url = "http://news.zing.vn/"
    zingFeed.urlRegex = List("*/news.zing.vn/*").asJava
    zingFeed.enable = true
    mongoOperations.save(zingFeed)
    assert(zingFeed.id != null)

    parserFactory.install(classOf[ZingPlugin].getName)
  }
}
