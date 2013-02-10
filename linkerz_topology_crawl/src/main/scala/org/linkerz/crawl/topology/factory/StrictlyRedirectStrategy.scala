package org.linkerz.crawl.topology.factory

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

  var lastRedirectedUri: String = _

  override def createLocationURI(location: String) = {
    val newLocation = UrlBuilder.fromString(location).toString
    lastRedirectedUri = newLocation
    super.createLocationURI(newLocation)
  }

}
