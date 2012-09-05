/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.bot.plugin

import org.linkerz.mongodb.model.ParserPluginData
import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import grizzled.slf4j.Logging
import org.jsoup.nodes.Document
import org.linkerz.core.matcher.SimpleRegexMatcher
import org.linkerz.crawler.core.parser.DefaultParser
import org.apache.commons.lang.StringUtils
import org.linkerz.crawler.core.job.CrawlJob
import edu.uci.ics.crawler4j.url.URLCanonicalizer

/**
 * The Class ParserPlugin.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:52 PM
 *
 */

trait ParserPlugin extends DefaultParser with Logging {

  var _pluginData: ParserPluginData = _

  def isMatch(url: String): Boolean = {
    assert(url != null)
    SimpleRegexMatcher.matcher(url, pluginData.urlRegex)
  }

  def beforeParse(crawlJob: CrawlJob, doc: Document): Boolean = true

  def afterParse(crawlJob: CrawlJob, doc: Document) {
    val webPage = crawlJob.result.get

    //Log error
    if (StringUtils.isBlank(webPage.title)) {
      crawlJob.error("Can not parse the title for " + webPage.webUrl.url)
    }

    if (StringUtils.isBlank(webPage.description)) {
      crawlJob.error("Can not parse the description for " + webPage.webUrl.url)
    }

    if (StringUtils.isBlank(webPage.featureImageUrl)) {
      crawlJob.error("Can not parse the image for " + webPage.webUrl.url)
    }
  }


  override def parse(crawlJob: CrawlJob) {
    super.parse(crawlJob)
    val webPage = crawlJob.result.get

    val inputStream = new ByteArrayInputStream(webPage.content)
    val doc = Jsoup.parse(inputStream, webPage.contentEncoding, webPage.webUrl.domainName)

    if (!beforeParse(crawlJob, doc)) {
      //Skip if any error exist
      return
    }

    val title = doc.select(pluginData.titleSelection).first()
    val description = doc.select(pluginData.descriptionSelection).first()
    val img = doc.select(pluginData.imgSelection).first()

    var titleText = ""
    if (title != null) {
      titleText = title.text()
      if (pluginData.titleAttName != null
        && pluginData.titleAttName.trim.length > 0) {
        titleText = title.attr(pluginData.titleAttName)
      }
    }

    if (titleText.length > pluginData.titleMaxLength
      && pluginData.titleMaxLength > 0) {
      titleText = titleText.substring(0, pluginData.titleMaxLength)
    }

    //To make sure the title will be never empty
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = doc.title()
    }

    //Recheck again
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = webPage.webUrl.url
    }

    var descriptionText = ""
    if (description != null) {
      descriptionText = description.text()
      if (pluginData.descriptionAttName != null
        && pluginData.descriptionAttName.trim.length > 0) {
        descriptionText = description.attr(pluginData.descriptionAttName)
      }
    }

    if (descriptionText.length > pluginData.descriptionMaxLength
      && pluginData.descriptionMaxLength > 0) {
      descriptionText = descriptionText.substring(0, pluginData.descriptionMaxLength)
    }

    if (descriptionText == null || descriptionText.trim.isEmpty) {
      descriptionText = titleText
    }

    webPage.title = titleText
    webPage.description = descriptionText

    var imgSrc = ""
    if (img != null) {
      imgSrc = img.attr("src")
      if (StringUtils.isNotBlank(imgSrc)) {
        webPage.featureImageUrl = URLCanonicalizer.getCanonicalURL(imgSrc, webPage.webUrl.baseUrl)
      }
    }

    afterParse(crawlJob, doc)
  }

  /**
   * Data for the plugin
   * @return
   */
  def pluginData: ParserPluginData = {
    if (_pluginData == null) {
      return defaultData
    }
    _pluginData
  }

  /**
   * Setter for the data
   * @param pluginData
   */
  def pluginData_=(pluginData: ParserPluginData) {
    _pluginData = pluginData
  }

  /**
   * Default data.
   * @return
   */
  def defaultData: ParserPluginData
}