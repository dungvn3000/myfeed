package vn.myfeed.crawl.topology.factory

/**
 * The Class FirefoxUserAgent.
 *
 * @author Nguyen Duc Dung
 * @since 5/7/13 11:20 PM
 *
 */
case class UserAgent(value: String)

object FireFoxUserAgent extends UserAgent("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2")