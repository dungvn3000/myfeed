package org.linkerz.crawler.core.download

import org.linkerz.crawler.core.model.WebLink

/**
 * The Class Downloader.
 *
 * @author Nguyen Duc Dung
 * @since 7/2/12, 11:51 PM
 *
 */

trait Downloader {

  /**
   * Download for a link.
   * @param webLink
   * @return
   */
  def download(webLink: WebLink): DownloadResult

}
