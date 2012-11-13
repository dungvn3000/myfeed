/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.bot.plugin

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

  def isMatch(url: String): Boolean = {
    assert(url != null)
    SimpleRegexMatcher.matcher(url, pluginData.urlRegex)
  }

  def beforeParse(crawlJob: CrawlJob, doc: Document): Boolean = {
    if (doc.select(pluginData.contentSelection).isEmpty) {
      crawlJob.code = CrawlJob.SKIP
      crawlJob.info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      return false
    }
    true
  }

  def afterParse(crawlJob: CrawlJob, doc: Document) {
    val webPage = crawlJob.result.get

    //Log error
    if (StringUtils.isBlank(webPage.title)) {
      crawlJob.error("Can not parse the title for " + webPage.webUrl.url)
    }

//    if (webPage.description.isEmpty || StringUtils.isBlank(webPage.description.get)) {
//      crawlJob.error("Can not parse the description for " + webPage.webUrl.url)
//    }

    if (webPage.featureImageUrl.isEmpty || StringUtils.isBlank(webPage.featureImageUrl.get)) {
      crawlJob.error("Can not parse the image for " + webPage.webUrl.url)
    }

    webPage.parsed = !crawlJob.isError

    onFinished(crawlJob)
  }

  def onFinished(crawlJob: CrawlJob) {}


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
//    val description = doc.select(pluginData.descriptionSelection).first()
    val content = doc.select(pluginData.contentSelection)
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
      titleText = titleText.substring(0, pluginData.titleMaxLength) + "..."

    }

    //To make sure the title will be never empty
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = doc.title()
    }

    //Recheck again
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = webPage.webUrl.url
    }

//    var descriptionText = ""
//    if (description != null) {
//      descriptionText = description.text()
//      if (pluginData.descriptionAttName != null
//        && pluginData.descriptionAttName.trim.length > 0) {
//        descriptionText = description.attr(pluginData.descriptionAttName)
//      }
//    }
//
//    if (descriptionText.length > pluginData.descriptionMaxLength
//      && pluginData.descriptionMaxLength > 0) {
//      descriptionText = descriptionText.substring(0, pluginData.descriptionMaxLength) + "..."
//    }
//
//    if (descriptionText == null || descriptionText.trim.isEmpty) {
//      descriptionText = titleText
//    }

    var text = ""
    if (content != null) {
      text = content.text()
      if (StringUtils.isNotBlank(text)) webPage.text = Some(text)
    }

    webPage.title = titleText
//    webPage.description = Some(descriptionText)

    var imgSrc = ""
    if (img != null) {
      imgSrc = img.attr("src")
      if (StringUtils.isNotBlank(imgSrc)) {
        val url = URLCanonicalizer.getCanonicalURL(imgSrc, webPage.webUrl.baseUrl)
        if(StringUtils.isNotBlank(url)) webPage.featureImageUrl = Some(url)
      }
    }

    afterParse(crawlJob, doc)
  }

  /**
   * Plugin data.
   * @return
   */
  def pluginData: ParserPluginData
}