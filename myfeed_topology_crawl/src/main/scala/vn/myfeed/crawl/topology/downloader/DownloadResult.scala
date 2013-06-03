package vn.myfeed.crawl.topology.downloader


/**
 * This class is a wrapper of FetchResult for serializable propose.
 *
 * @author Nguyen Duc Dung
 * @since 5/7/13 10:39 PM
 *
 */
case class DownloadResult(url: String, content: Array[Byte])