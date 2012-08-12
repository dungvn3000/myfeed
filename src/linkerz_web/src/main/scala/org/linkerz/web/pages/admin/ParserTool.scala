/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.pages.admin

import org.apache.tapestry5.annotations.{InjectComponent, Persist, Property}
import org.linkerz.mongodb.model.{ParserPluginData, Link}
import org.apache.tapestry5.corelib.components.{Form, Zone}
import java.util
import grizzled.slf4j.Logging
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.crawler.core.model.{WebPage, WebUrl}
import collection.mutable.ListBuffer
import org.linkerz.crawler.core.parser.ParserResult
import org.linkerz.crawler.bot.matcher.SimpleRegexMatcher
import org.linkerz.web.services.plugin.PluginService
import org.apache.tapestry5.ioc.annotations.Inject
import org.linkerz.web.services.parser.ParserService
import org.linkerz.crawler.core.downloader.DefaultDownload


/**
 * The Class Admin.
 *
 * @author Nguyen Duc Dung
 * @since 8/10/12, 2:54 AM
 *
 */

class ParserTool extends Logging {

  @Property
  var numberOfUrl: Int = 10

  @Property
  var webPages = new util.ArrayList[WebPage]()

  @Property
  var webPage: WebPage = _

  @InjectComponent
  var linksZone: Zone = _

  @InjectComponent
  var parserForm: Form = _

  @InjectComponent
  var testForm: Form = _

  @Persist
  var parseData: ParserPluginData = _

  @Inject
  var pluginService: PluginService = _

  @Inject
  var parserService: ParserService = _

  def onActivate(id: String) {
    parseData = pluginService.findParserPlugin(id)
  }

  def onSubmitFromParserForm() {
    println("parseData = " + parseData.imgSelection)
  }

  def onActionFromCancelBtn() {
    webPages.clear()
  }

  def onSubmitFromTestForm() = {
    val urlList = new ListBuffer[WebUrl]

    val parser = parserService.getParser(parseData.pluginClass)
    parser.pluginData = parseData
    val fetcher = new Fetcher(new DefaultDownload, parser)
    val beginUrl = new WebUrl(parseData.urlTest)
    val fetchResult = fetcher.fetch(beginUrl)

    urlList += beginUrl

    var count = 0
    var index = 0
    while (count != numberOfUrl && fetchResult.webPage.webUrls.size > index) {
      if (SimpleRegexMatcher.matcher(fetchResult.webPage.webUrls(index).url, parseData.urlRegex)
        && !urlList.contains(fetchResult.webPage.webUrls(index))) {
        val result = fetcher.fetch(fetchResult.webPage.webUrls(index))

        result.code match {
          case ParserResult.DONE => webPages.add(result.webPage)
          case ParserResult.SKIP => {
            info("Skip this link " + result.webPage.webUrl.url)
            info(result.info.mkString)
          }
          case ParserResult.ERROR => {
            error("Some thing worng " + result.webPage.webUrl.url)
            error(result.error.mkString)
          }
        }

        urlList += fetchResult.webPage.webUrls(index)
        count += 1
      }
      index += 1
    }

    linksZone
  }


}
