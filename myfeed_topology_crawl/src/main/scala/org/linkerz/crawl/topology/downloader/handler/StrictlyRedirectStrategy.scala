package org.linkerz.crawl.topology.downloader.handler

import org.apache.http.impl.client.DefaultRedirectStrategy
import gumi.builders.UrlBuilder

/**
 * The Class StrictlyRedirectStrategy.
 *
 * @author Nguyen Duc Dung
 * @since 12/28/12 2:21 AM
 *
 */
class StrictlyRedirectStrategy extends DefaultRedirectStrategy {

  override def createLocationURI(location: String) = {
    val newLocation = UrlBuilder.fromString(location).toString
    super.createLocationURI(newLocation)
  }

}
