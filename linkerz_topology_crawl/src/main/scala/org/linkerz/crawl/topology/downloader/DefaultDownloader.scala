package org.linkerz.crawl.topology.downloader

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.linkerz.crawl.topology.model.WebUrl
import grizzled.slf4j.Logging

/**
 * The Class RssDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 2/10/13 12:10 AM
 *
 */
class DefaultDownloader(httpClient: HttpClient = new DefaultHttpClient) extends Logging {

  def download(webUrl: WebUrl) = {
    val response = httpClient.execute(new HttpGet(webUrl.toString))
    if (response.getStatusLine != null) info("Download " + response.getStatusLine.getStatusCode + " : " + webUrl)
    response
  }

}
