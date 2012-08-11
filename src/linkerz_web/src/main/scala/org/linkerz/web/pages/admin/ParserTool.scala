/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.web.pages.admin

import org.apache.tapestry5.annotations.{InjectComponent, Persist, Property}
import org.linkerz.crawler.bot.plugin.VnExpressPlugin
import org.linkerz.mongodb.model.{ParserPlugin, Link}
import org.apache.tapestry5.corelib.components.{Form, Zone}
import java.util
import grizzled.slf4j.Logging
import org.linkerz.crawler.core.fetcher.Fetcher
import org.linkerz.crawler.core.model.WebUrl
import collection.mutable.ListBuffer
import org.linkerz.crawler.core.parser.ParserResult
import org.linkerz.crawler.bot.matcher.SimpleRegexMatcher
import org.linkerz.web.services.plugin.PluginService
import org.apache.tapestry5.ioc.annotations.Inject


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
  var urlTest: String = _

  @Persist
  @Property
  var numberOfUrl: Int = _

  @Property
  var links = new util.ArrayList[Link]()

  @Property
  var link: Link = _

  @InjectComponent
  var linksZone: Zone = _

  @InjectComponent
  var parserForm: Form = _

  @InjectComponent
  var testForm: Form = _

  @Persist
  var parseData: ParserPlugin = _

  @Inject
  var pluginService: PluginService = _

  def onActivate (id: String) {
    parseData = pluginService.findParserPlugin(id)
  }

  def onSubmitFromParserForm() {
    if (parseData == null) {
      parseData = new ParserPlugin
    }
  }

  def onActionFromCancelBtn() {
    links.clear()
  }

  def onSubmitFromTestForm() = {

    val fetchResults = new ListBuffer[ParserResult]
    val urlList = new ListBuffer[WebUrl]
    val fetcher = new Fetcher
    val beginUrl = new WebUrl(urlTest)
    val fetchResult = fetcher.fetch(beginUrl)

    urlList += beginUrl
    fetchResults += fetchResult

    var count = 0
    var index = 0
    while (count != numberOfUrl && fetchResult.webUrls.size > index) {
      if (SimpleRegexMatcher.matcher(fetchResult.webUrls(index).url, parseData.urlRegex)
        && !urlList.contains(fetchResult.webUrls(index))) {
        fetchResults += fetcher.fetch(fetchResult.webUrls(index))
        urlList += fetchResult.webUrls(index)
        count += 1
      }
      index += 1
    }

    val parser = new VnExpressPlugin
    parser.pluginData = parseData

    fetchResults.foreach(web => {
      val link = web.webPage.asLink()
      if (parser.isMatch(link)) {
        if (parser.parse(link)) {
          links.add(link)
        } else {
          info("Some thing worng " + link.url)
        }
      } else {
        info("The link is not match " + link.url)
      }
    })

    linksZone
  }


}
