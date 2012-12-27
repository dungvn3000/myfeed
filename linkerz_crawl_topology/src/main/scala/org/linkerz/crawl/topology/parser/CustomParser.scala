/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.parser

import java.io.ByteArrayInputStream
import org.jsoup.Jsoup
import grizzled.slf4j.Logging
import org.jsoup.nodes.Document
import org.linkerz.core.matcher.SimpleRegexMatcher
import org.apache.commons.lang.StringUtils
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import org.linkerz.model.NewFeed
import org.linkerz.crawl.topology.job.CrawlJob
import gumi.builders.UrlBuilder

/**
 * The Class CustomParser.
 *
 * @author Nguyen Duc Dung
 * @since 8/11/12, 1:52 PM
 *
 */

case class CustomParser(data: NewFeed) extends DefaultParser with Logging {

  def isMatch(url: String): Boolean = {
    assert(url != null)
    SimpleRegexMatcher.matcher(url, data.urlRegex)
  }

  def beforeParse(crawlJob: CrawlJob, doc: Document): Boolean = {
    if (doc.select(data.contentSelection).isEmpty) {
      crawlJob.info("Skip it, cause it is not a new detail page ", getClass.getName, crawlJob.webUrl)
      info("Skip it, cause it is not a new detail page " + crawlJob.webUrl.url)
      return false
    }
    true
  }

  def afterParse(crawlJob: CrawlJob, doc: Document) {
    val webPage = crawlJob.result.get

    if (webPage.featureImageUrl.isEmpty || StringUtils.isBlank(webPage.featureImageUrl.get)) {
      //Set default image if the article has no image.
      webPage.featureImageUrl = Some(data.defaultImgUrl)
    }

    //Log error
    if (StringUtils.isBlank(webPage.title)) {
      crawlJob.error("Can not parse the title", getClass.getName, crawlJob.webUrl)
    }

    if (webPage.featureImageUrl.isEmpty || StringUtils.isBlank(webPage.featureImageUrl.get)) {
      crawlJob.error("Can not parse the image", getClass.getName, crawlJob.webUrl)
    }

    if (webPage.text.isEmpty || StringUtils.isBlank(webPage.text.get)) {
      crawlJob.error("Can not parse the content", getClass.getName, crawlJob.webUrl)
    }

//    webPage.parsed = !crawlJob.isError

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

    val title = doc.select(data.titleSelection).first()
    val content = doc.select(data.contentSelection)
    val img = doc.select(data.imgSelection).first()

    var titleText = ""
    if (title != null) {
      titleText = title.text()
    }

    //To make sure the title will be never empty
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = doc.title()
    }

    //Recheck again
    if (titleText == null || titleText.trim.isEmpty) {
      titleText = webPage.webUrl.url
    }

    var text = ""
    if (content != null) {
      text = content.text()
      if (StringUtils.isNotBlank(text)) webPage.text = Some(text)
    }

    webPage.title = titleText

    var imgSrc = ""
    if (img != null) {
      imgSrc = UrlBuilder.fromString(img.attr("src")).toString
      if (StringUtils.isNotBlank(imgSrc)) {
        val url = URLCanonicalizer.getCanonicalURL(imgSrc, webPage.webUrl.baseUrl)
        if (StringUtils.isNotBlank(url)) webPage.featureImageUrl = Some(url)
      }
    }

    afterParse(crawlJob, doc)
  }

}