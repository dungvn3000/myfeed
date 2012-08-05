/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser

import org.scalatest.FunSuite
import de.l3s.boilerpipe.extractors.CommonExtractors
import de.l3s.boilerpipe.sax.HTMLHighlighter
import java.io.PrintWriter
import java.net.URL

/**
 * The Class TestBoilerpipe.
 *
 * @author Nguyen Duc Dung
 * @since 8/5/12, 2:27 PM
 *
 */

class TestBoilerpipe extends FunSuite {

  test("testExtractorFormAmazon") {

    val extractor = CommonExtractors.ARTICLE_EXTRACTOR
    val hh = HTMLHighlighter.newExtractingInstance

    val out = new PrintWriter(System.out)

    val extractedHtml = hh.process(new URL("http://www.amazon.com/gp/product/1847193072/" +
      "ref=s9_simh_gw_p14_d3_i1?pf_rd_m=ATVPDKIKX0DER&pf_rd_s=center-3" +
      "&pf_rd_r=011VTMZBV83T5Y56CDTD&pf_rd_t=101&pf_rd_p=470938811&pf_rd_i=507846"), extractor)

    out.println(extractedHtml)

    out.close()
  }

}
