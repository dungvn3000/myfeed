package org.linkerz.crawl.topology.downloader

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpGet
import org.linkerz.crawl.topology.model.WebUrl
import grizzled.slf4j.Logging
import org.apache.http.client.entity.GzipDecompressingEntity

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

    var entity = response.getEntity

    if (entity != null && entity.getContentEncoding != null) {
      if (entity.getContentEncoding.toString.contains("gzip")) {
        entity = new GzipDecompressingEntity(entity)
        response.setEntity(entity)
      }
    }

    response
  }

}
