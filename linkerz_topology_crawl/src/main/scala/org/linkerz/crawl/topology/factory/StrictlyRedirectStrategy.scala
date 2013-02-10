package org.linkerz.crawl.topology.factory

import org.apache.http.impl.client.DefaultRedirectStrategy
import gumi.builders.UrlBuilder
import org.apache.http.{HttpResponse, HttpRequest}
import org.apache.http.protocol.HttpContext
import java.net.URI

/**
 * The Class StrictlyRedirectStrategy.
 *
 * @author Nguyen Duc Dung
 * @since 12/28/12 2:21 AM
 *
 */
class StrictlyRedirectStrategy extends DefaultRedirectStrategy {

  var lastRedirectedUri: URI = _

  override def getLocationURI(request: HttpRequest, response: HttpResponse, context: HttpContext) = {
    lastRedirectedUri = super.getLocationURI(request, response, context)
    lastRedirectedUri
  }

  override def createLocationURI(location: String) = {
    val newLocation = UrlBuilder.fromString(location).toString
    super.createLocationURI(newLocation)
  }

}
