/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.testdata

import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.model.model.{User, NewFeed}
import collection.JavaConverters._
import org.linkerz.crawler.bot.factory.ParserPluginFactory
import org.junit.experimental.categories.Category
import org.junit.{Assert, Test}
import org.linkerz.crawler.bot.plugin.parser._

/**
 * The Class TestData.
 *
 * @author Nguyen Duc Dung
 * @since 8/18/12, 5:22 AM
 *
 */
class TestData {

//  val mongoOperations = context.getBean("mongoTemplate", classOf[MongoOperations])
//  val parserFactory = context.getBean("parserFactory", classOf[ParserPluginFactory])

  @Test
  def testAddVNExpress() {
//    val vnExpressFeed = new NewFeed
//    vnExpressFeed.name = "VnExpress.net"
//    vnExpressFeed.group = "vnexpress.net"
//    vnExpressFeed.time = 15
//    vnExpressFeed.url = "http://vnexpress.net/"
//    vnExpressFeed.urlRegex = List("*/vnexpress.net/*/*/2012/*").asJava
//    vnExpressFeed.excludeUrl = List(
//      "*/vnexpress.net/*/cuoi/*",
//      "*/vnexpress.net/gl/ban-doc-viet/tam-su/*").asJava
//    vnExpressFeed.enable = true
//    mongoOperations.save(vnExpressFeed)
//    assert(vnExpressFeed.id != null)
//
//    parserFactory.install(classOf[VnExpressPlugin].getName)
  }

  @Test
  def testAddZing() {
//    val zingFeed = new NewFeed
//    zingFeed.name = "Zing News"
//    zingFeed.group = "zing.vn"
//    zingFeed.time = 15
//    zingFeed.url = "http://news.zing.vn/"
//    zingFeed.urlRegex = List("*/news.zing.vn/*").asJava
//    zingFeed.enable = true
//    mongoOperations.save(zingFeed)
//    assert(zingFeed.id != null)
//
//    parserFactory.install(classOf[ZingPlugin].getName)
  }

  @Test
  def testAddGenK() {
//    val genKFeed = new NewFeed
//    genKFeed.name = "GenK"
//    genKFeed.group = "genk.vn"
//    genKFeed.time = 15
//    genKFeed.url = "http://genk.vn/"
//    genKFeed.urlRegex = List("*/genk.vn/*").asJava
//    genKFeed.excludeUrl = List("*/genk.vn/hoi-dap*").asJava
//    genKFeed.enable = true
//    mongoOperations.save(genKFeed)
//    assert(genKFeed.id != null)
//
//    parserFactory.install(classOf[GenKPlugin].getName)
  }

  @Test
  def testAddHOnline() {
//    val hOnline = new NewFeed
//    hOnline.name = "H-Online"
//    hOnline.group = "h-online.com"
//    hOnline.time = 15
//    hOnline.url = "http://www.h-online.com/"
//    hOnline.urlRegex = List("*/www.h-online.com/*").asJava
//    hOnline.enable = true
//    mongoOperations.save(hOnline)
//    assert(hOnline.id != null)
//
//    parserFactory.install(classOf[HOnlinePlugin].getName)
  }

  @Test
  def testAddJavaDZone() {
//    val javaDZone = new NewFeed
//    javaDZone.name = "Java - DZone"
//    javaDZone.group = "dzone.com"
//    javaDZone.time = 15
//    javaDZone.url = "http://java.dzone.com/"
//    javaDZone.urlRegex = List("*/java.dzone.com/*").asJava
//    javaDZone.enable = true
//    mongoOperations.save(javaDZone)
//    assert(javaDZone.id != null)
//
//    parserFactory.install(classOf[JavaDZonePlugin].getName)
  }

  @Test
  def testAdd24h() {
//    val twentyFour = new NewFeed
//    twentyFour.name = "24H"
//    twentyFour.group = "24h.com.vn"
//    twentyFour.time = 15
//    twentyFour.url = "http://hcm.24h.com.vn/"
//    twentyFour.urlRegex = List("*/*.24h.com.vn/*").asJava
//    twentyFour.enable = true
//    mongoOperations.save(twentyFour)
//    assert(twentyFour.id != null)
//
//    parserFactory.install(classOf[TwentyFourHourPlugin].getName)
  }

  @Test
  def testAddUser() {
//    val user = new User
//    user.userName = "dungvn3000"
//    mongoOperations.save(user)
//
//    Assert.assertEquals(false, user.id == null)
  }
}
