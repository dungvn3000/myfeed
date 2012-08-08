/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.language.detect

import org.scalatest.FunSuite
import org.linkerz.test.spring.SpringContext
import org.springframework.data.mongodb.core.MongoOperations
import org.linkerz.mongodb.model.Link
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput
import org.xml.sax.InputSource
import java.io.ByteArrayInputStream

import collection.JavaConversions._
import vietnamese.WordClean

/**
 * The Class TestGetPageContent.
 *
 * @author Nguyen Duc Dung
 * @since 8/8/12, 3:06 AM
 *
 */

class TestGetPageContent extends FunSuite with SpringContext {

  test("testGetPageContentInDB") {
    val mongoOperations = context.getBean("mongoTemplate", classOf[MongoOperations])
    val links = mongoOperations.findAll(classOf[Link])
    assert(links.size > 0)
    val inputStream = new ByteArrayInputStream(links.last.content)
    val doc = new BoilerpipeSAXInput(new InputSource(inputStream)).getTextDocument

    doc.getTextBlocks.foreach(block => {
      println(WordClean.clean(block.getText))
    })

  }

}
