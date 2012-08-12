/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.scalatest.FunSuite
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.{ParserPluginData, Link}
import org.linkerz.test.spring.SpringContext

import collection.JavaConversions._
import vnexpress.VnExpressPlugin
import org.linkerz.crawler.core.parser.ParserResult

/**
 * The Class TestVnExpressPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:59 PM
 *
 */

class TestVnExpressPlugin extends FunSuite with SpringContext {

  val plugin = new VnExpressPlugin
  //Prepare data
  val parseData = new ParserPluginData
  parseData.urlRegex = "*/vnexpress.net/*/*/*/index.html"
  parseData.titleSelection = "title"
  parseData.descriptionSelection = "head meta[name=description]"
  parseData.descriptionAttName = "content"
  parseData.imgSelection = "img.img-logo"

  plugin.pluginData = parseData

  test("testPlugin") {


  }

}
