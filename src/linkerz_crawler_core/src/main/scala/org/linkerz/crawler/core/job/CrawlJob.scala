package org.linkerz.crawler.core.job

import org.linkerz.crawler.core.download.Downloader
import org.linkerz.crawler.core.parser.{BaseParserResult, Parser}

import org.linkerz.crawler.core.model.WebLink

/**
 * The Class CrawlJob.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 10:45 PM
 *
 */

class CrawlJob(_downloaders: Map[String, Downloader], _parsers: Map[String, Parser], _webLink: WebLink) extends Job {

  var downloaders = _downloaders
  var parsers = _parsers
  var webLink = _webLink

  def execute() {
    val downloadResult = downloaders.head._2.download(webLink)
    val parserResult = parsers.head._2.parse(downloadResult)
    parserResult.asInstanceOf[BaseParserResult].webLink.foreach(webLink => {
      println(webLink.url)
    })
  }
}
