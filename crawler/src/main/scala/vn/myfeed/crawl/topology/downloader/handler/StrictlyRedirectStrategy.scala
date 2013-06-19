package vn.myfeed.crawl.topology.downloader.handler

import org.apache.http.impl.client.DefaultRedirectStrategy
import gumi.builders.UrlBuilder
import org.apache.commons.validator.routines.UrlValidator

/**
 * The Class StrictlyRedirectStrategy.
 *
 * @author Nguyen Duc Dung
 * @since 12/28/12 2:21 AM
 *
 */
class StrictlyRedirectStrategy extends DefaultRedirectStrategy {

  val urlValidator = new UrlValidator
  var lastRedirectedUri: Option[String] = None

  override def createLocationURI(location: String) = {
    val newLocation = UrlBuilder.fromString(location).toString
    if(urlValidator.isValid(newLocation)) {
      lastRedirectedUri = Some(newLocation)
      super.createLocationURI(newLocation)
    } else {
      super.createLocationURI(location)
    }
  }

}
