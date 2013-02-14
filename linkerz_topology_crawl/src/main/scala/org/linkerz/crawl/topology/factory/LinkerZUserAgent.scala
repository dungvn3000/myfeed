package org.linkerz.crawl.topology.factory

import crawlercommons.fetcher.http.UserAgent

/**
 * The Class LinkerZUserAgent.
 *
 * @author Nguyen Duc Dung
 * @since 2/14/13 11:59 PM
 *
 */
class LinkerZUserAgent extends UserAgent("linkerz crawler","","") {
  override def getUserAgentString = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2"
}
