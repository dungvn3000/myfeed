package org.linkerz.crawl.topology.downloader

import com.ning.http.client.AsyncHttpClient
import org.apache.http.HttpStatus

/**
 * The Class AsyncDownloader.
 *
 * @author Nguyen Duc Dung
 * @since 5/8/13 5:13 AM
 *
 */
class AsyncDownloader(httpClient: AsyncHttpClient = new AsyncHttpClient()) extends Downloader {
  def download(url: String): Option[DownloadResult] = {
    val response = httpClient.prepareGet(url).execute().get()
    if (response.getStatusCode == HttpStatus.SC_OK) {
      val content = response.getResponseBodyAsBytes
      if (content != null && content.length > 0) {
        return Some(DownloadResult(
          url = url,
          content = content
        ))
      }
    }
    None
  }
}
