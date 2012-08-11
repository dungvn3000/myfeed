/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.pages.admin

import org.apache.tapestry5.annotations.{InjectComponent, Persist, Property}
import org.linkerz.crawler.bot.plugin.VnExpressPlugin
import org.linkerz.mongodb.model.{Link, ParseData}
import org.apache.tapestry5.corelib.components.{Form, Zone}
import java.util
import grizzled.slf4j.Logging
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.crawler.core.model.WebUrl
import collection.mutable.ListBuffer
import org.linkerz.crawler.core.parser.ParserResult
import org.linkerz.crawler.bot.matcher.SimpleRegexMatcher
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import com.googlecode.flaxcrawler.utils.UrlUtils
import org.jsoup.nodes.Document


/**
 * The Class Admin.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 2:54 AM
 *
 */

class ParserTool extends Logging {

  @Persist
  @Property
  var urlRegex: String = _

  @Persist
  @Property
  var titleSelection: String = _

  @Persist
  @Property
  var titleAttName: String = _

  @Persist
  @Property
  var titleMaxLength: Int = _

  @Persist
  @Property
  var descriptionSelection: String = _

  @Persist
  @Property
  var descriptionAttName: String = _

  @Persist
  @Property
  var descriptionMaxLength: Int = _

  @Persist
  @Property
  var contentSelection: String = _

  @Persist
  @Property
  var contentAttName: String = _

  @Persist
  @Property
  var contentMaxLength: Int = _

  @Persist
  @Property
  var imgSelection: String = _

  @Persist
  @Property
  var urlTest: String = _

  @Persist
  @Property
  var numberOfUrl: Int = _

  @Persist
  @Property
  var title: String = _

  @Persist
  @Property
  var description: String = _

  @Persist
  @Property
  var img: String = _

  @Property
  var links = new util.ArrayList[Link]()

  @Property
  var link: Link = _

  @InjectComponent
  var updateZone: Zone = _

  @InjectComponent
  var downloadZone: Zone = _

  @Persist
  @Property
  var downloadUrl: String = _

  @InjectComponent
  var parserForm: Form = _

  @InjectComponent
  var downloadForm: Form = _

  def onSubmitFromParserForm() = {

    val linkParseData = new ParseData
    linkParseData.urlRegex = urlRegex

    linkParseData.titleAttName = titleAttName
    linkParseData.titleMaxLength = titleMaxLength
    linkParseData.titleSelection = titleSelection

    linkParseData.descriptionAttName = descriptionAttName
    linkParseData.descriptionMaxLength = descriptionMaxLength
    linkParseData.descriptionSelection = descriptionSelection

    linkParseData.imgSelection = imgSelection

    val fetchResults = new ListBuffer[ParserResult]
    val fetcher = new Fetcher
    val fetchResult = fetcher.fetch(new WebUrl(urlTest))

    fetchResults += fetchResult

    var count = 0
    var index = 0
    while (count != numberOfUrl && fetchResult.webUrls.size > index) {
      if (SimpleRegexMatcher.matcher(fetchResult.webUrls(index).url, urlRegex)) {
        fetchResults += fetcher.fetch(fetchResult.webUrls(index))
        count += 1
      }
      index += 1
    }

    val parser = new VnExpressPlugin
    fetchResults.foreach(web => {
      val link = web.webPage.asLink()
      val result = parser.parse(link)
      if (result) {
        links.add(link)
      } else {
        info("Some thing wrong " + web.webPage.webUrl.url)
      }
    })

    updateZone
  }

  def onActionFromCancelBtn() {
    links.clear()
  }

  @Persist
  var fetcher: Fetcher = _

  @Persist
  var doc: Document = _

  def onSubmitFromDownloadForm() = {
    fetcher = new Fetcher
    val fetchResult = fetcher.fetch(new WebUrl(downloadUrl))
    val link = fetchResult.webPage.asLink
    val inputStream = new ByteArrayInputStream(link.content)
    doc = Jsoup.parse(inputStream, link.contentEncoding, UrlUtils.getDomainName(link.url))

    title = doc.title

    downloadZone
  }

  def onActionFromNextTitleBtn() {

    title = doc.title

    info(doc.getAllElements.iterator.next.toString)

  }

}
