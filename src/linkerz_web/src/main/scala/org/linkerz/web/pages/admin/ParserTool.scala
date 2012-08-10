/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.pages.admin

import org.apache.tapestry5.annotations.{InjectComponent, Persist, Property}
import org.linkerz.crawler.bot.parser.{LinkerZParserResult, LinkerZParser}
import org.linkerz.mongodb.model.LinkParseData
import org.apache.tapestry5.corelib.components.Zone
import java.util
import grizzled.slf4j.Logging
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.crawler.core.model.WebUrl
import collection.mutable.ListBuffer
import org.linkerz.crawler.core.parser.ParserResult
import org.linkerz.crawler.bot.matcher.SimpleRegexMatcher


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
  var links = new util.ArrayList[LinkerZParserResult]()

  @Property
  var link: LinkerZParserResult = _

  @InjectComponent
  var updateZone: Zone = _

  def onSubmit() = {

    val linkParseData = new LinkParseData
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

    val parser = new LinkerZParser
    fetchResults.foreach(web => {
      val result = parser.parse(web.webPage.asLink(), linkParseData)
      if (result != null && result.imgSrc != null) {
        links.add(result)
      } else {
        info("can not get image " + web.webPage.webUrl.url)
      }
    })

    updateZone
  }


}
