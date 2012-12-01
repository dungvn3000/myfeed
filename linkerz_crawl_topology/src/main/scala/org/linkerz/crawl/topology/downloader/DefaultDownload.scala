/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.downloader

import com.ning.http.client.AsyncHttpClient
import org.apache.commons.lang.StringUtils
import org.linkerz.crawl.topology.job.CrawlJob
import org.linkerz.crawl.topology.model.WebPage
import org.apache.commons.httpclient.HttpStatus
import edu.uci.ics.crawler4j.url.URLCanonicalizer

/**
 * The Class DefaultDownload.
 *
 * @author Nguyen Duc Dung
 * @since 8/12/12, 11:07 PM
 *
 */

class DefaultDownload(httpClient: AsyncHttpClient) extends Downloader {

  def download(crawlJob: CrawlJob) {
    val webUrl = crawlJob.webUrl
    val webPage = new WebPage

    val response = httpClient.prepareGet(webUrl.url).execute().get()
    info("Download " + response.getStatusCode + " : " + webUrl.url)

    if (response.getStatusCode == HttpStatus.SC_MOVED_PERMANENTLY
      || response.getStatusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
      val location = response.getHeader("Location")
      if (StringUtils.isNotBlank(location)) {
        webUrl.movedToUrl = URLCanonicalizer.getCanonicalURL(location, webUrl.baseUrl)
      }
    } else if (response.getStatusCode == HttpStatus.SC_OK) {
      webPage.content = response.getResponseBodyAsBytes
      webPage.contentType = response.getContentType
    }

    webPage.webUrl = webUrl
    webPage.responseCode = response.getStatusCode

    crawlJob.result = Some(webPage)
  }

}
