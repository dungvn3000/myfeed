/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot

import org.scalatest.FunSuite
import org.linkerz.mongodb.model.{Link, LinkParseData}
import parser.LinkerZParser
import org.linkerz.test.spring.SpringContext
import org.springframework.data.mongodb.core.MongoOperations

/**
 * The Class TestParserWithEntity.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 12:20 AM
 *
 */

class TestParserWithEntity extends FunSuite with SpringContext {

  test("testParser1") {

    val mongoOperations = context.getBean("mongoTemplate", classOf[MongoOperations])
    val links = mongoOperations.findAll(classOf[Link])

    //Prepare data
    val linkParseData = new LinkParseData
    linkParseData.urlRegex = "*/vnexpress.net/*/*/*/index.html"
    linkParseData.titleSelection = "title"
    linkParseData.descriptionSelection = "head meta[name=description]"
    linkParseData.descriptionAttName = "content"
    linkParseData.contentSelection = "head meta[name=description]"
    linkParseData.contentAttName = "content"
    linkParseData.imgSelection = "img.img-logo"

    val parser = new LinkerZParser
    parser.parse(links.get(0), linkParseData)

  }

}
