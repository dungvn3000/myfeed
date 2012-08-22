/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot

import org.scalatest.FunSuite
import de.l3s.boilerpipe.extractors.{ExtractorBase, CommonExtractors}
import de.l3s.boilerpipe.sax.HTMLHighlighter
import java.io.PrintWriter
import java.net.URL
import de.l3s.boilerpipe.document.TextDocument

import collection.JavaConversions._
import org.linkerz.test.categories.ManualTest
import org.junit.experimental.categories.Category

/**
 * The Class TestExtractPageContent.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 4:22 AM
 *
 */
@Category(Array(classOf[ManualTest]))
class TestExtractPageContent extends FunSuite {

  test("testExtractDescription") {

    val extractor = new MyExtractor
    val hh = HTMLHighlighter.newExtractingInstance
    val extractedHtml = hh
      .process(new URL("http://www.dzone.com/links/index.html"), extractor)

  }

  class MyExtractor extends ExtractorBase {
    def process(doc: TextDocument) = {
      doc.getTextBlocks.foreach(block => {
        println(block.toString)
      })
      false
    }
  }

}
