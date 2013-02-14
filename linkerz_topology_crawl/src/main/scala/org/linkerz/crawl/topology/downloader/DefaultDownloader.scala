/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.model.WebPage
import crawlercommons.fetcher.BaseFetcher
import com.google.common.net.HttpHeaders
import org.apache.http.HttpStatus

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class DefaultDownloader(htmlFetcher: BaseFetcher) extends Downloader {

  def download(crawlJob: CrawlJob) {
    val webUrl = crawlJob.webUrl
    info("Download : " + webUrl)
    val result = htmlFetcher.get(webUrl.toString)
    if (result.getStatusCode == HttpStatus.SC_OK && result.getContentLength > 0) {
      val webPage = WebPage(webUrl)
      webPage.content = result.getContent
      webPage.contentType = result.getContentType
      webPage.contentEncoding = result.getHeaders.get(HttpHeaders.CONTENT_ENCODING)
      crawlJob.result = Some(webPage)
    }
    crawlJob.responseCode = result.getStatusCode
  }

}
