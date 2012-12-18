/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.parser

import grizzled.slf4j.Logging
import java.net.{URI, URL}
import org.junit.Test
import net.htmlparser.jericho.{HTMLElementName, Source}
import collection.JavaConversions._
import org.apache.http.client.utils.URIUtils

/**
 * The Class TestParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 10:58 PM
 *
 */
class TestParser extends Logging {

  @Test
  def testHtmlParser() {

    val source = new Source(new URL("http://www.tinhte.vn/threads/1747931/"))

    val links = source.getAllElements(HTMLElementName.A)

    links.foreach(link => {

      val href = link.getAttributeValue("href")

      val url = URIUtils.resolve(new URI("http://www.tinhte.vn"), href)

      println(url)
    })

  }

}
