/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.parser

import org.scalatest.FunSuite
import grizzled.slf4j.Logging
import org.linkerz.crawler.core.downloader.{DefaultDownload, Downloader}
import org.apache.tika.Tika
import java.net.URL
import org.apache.tika.sax.{BodyContentHandler, TeeContentHandler, LinkContentHandler}
import java.io.{PrintWriter, ByteArrayInputStream}
import org.linkerz.crawler.core.model.WebUrl
import org.apache.tika.parser.html.{DefaultHtmlMapper, IdentityHtmlMapper, HtmlMapper, HtmlParser}
import org.apache.tika.parser.ParseContext
import org.apache.tika.metadata.Metadata

/**
 * The Class TestParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/3/12, 10:58 PM
 *
 */

class TestParser extends FunSuite with Logging {

  val downloader = new DefaultDownload

  test("testSimpleParser") {
    val tika = new Tika
    val mine = tika.detect(new URL("http://localhost/vnexpress/vnexpress.net/"))
    println(mine)

    val content = tika.parseToString(new URL("http://localhost/vnexpress/vnexpress.net/"))
    println(content)
  }

  test("testPdfParser") {
    val tika = new Tika
    val mine = tika.detect(new URL("http://localhost/linkerz_test_data/pdf/fw4.pdf"))
    println(mine)

    val content = tika.parseToString(new URL("http://localhost/linkerz_test_data/pdf/fw4.pdf"))
    println(content)
  }

  test("testHtmlParser") {

    val downloadResult = downloader.download(new WebUrl("http://localhost/vnexpress/vnexpress.net/"))
    val inputStream = new ByteArrayInputStream(downloadResult.byteContent)

    val linkCollector = new LinkContentHandler
    val handler = new TeeContentHandler(new BodyContentHandler(new PrintWriter(System.out)), linkCollector)

    val metadata = new Metadata
    val parseContext = new ParseContext
    parseContext.set(classOf[HtmlMapper], new DefaultHtmlMapper())
    val htmlParser = new HtmlParser
    htmlParser.parse(inputStream, handler, metadata, parseContext)

    println(metadata.toString)
    println(metadata.get(Metadata.DESCRIPTION))
    println(metadata.get(Metadata.SUBJECT))
    println(metadata.get(Metadata.FORMAT))
    println(metadata.get(Metadata.TYPE))
    println(metadata.get("Content-Encoding"))
  }

}
