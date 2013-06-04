package vn.myfeed.crawl.topology.downloader

/**
 * The Class Downloader.
 *
 * @author Nguyen Duc Dung
 * @since 5/8/13 5:12 AM
 *
 */
trait Downloader {

  def download(url: String): Option[DownloadResult]

}
