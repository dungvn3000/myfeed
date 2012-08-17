/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.testdata

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.NewFeed
import collection.JavaConverters._

/**
 * The Class TestData.
 *
 * @author Nguyen Duc Dung
 * @since 8/18/12, 5:22 AM
 *
 */
class TestData extends FunSuite with SpringContext {

  val mongoOperations = context.getBean("mongoTemplate", classOf[MongoOperations])

  test("testAddNewFeed") {
    val vnExpressFeed = new NewFeed
    vnExpressFeed.name = "VnExpress.net"
    vnExpressFeed.group = "vnexpress"
    vnExpressFeed.time = 15
    vnExpressFeed.url = "http://vnexpress.net/"
    vnExpressFeed.urlRegex = List("*/vnexpress.net/*/*/2012/*").asJava
    vnExpressFeed.excludeUrl = List(
      "*/vnexpress.net/*/cuoi/*",
      "*/vnexpress.net/gl/ban-doc-viet/tam-su/*").asJava
    vnExpressFeed.enable = true
    mongoOperations.save(vnExpressFeed)
    assert(vnExpressFeed.id != null)
  }
}
