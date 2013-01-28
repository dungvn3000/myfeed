package org.linkerz.parser.model

import gumi.builders.UrlBuilder
import org.apache.http.client.utils.URIUtils
import java.net.URI

/**
 * This class represent for url of the www.
 *
 * @author Nguyen Duc Dung
 * @since 1/27/13 4:03 AM
 *
 */
case class WebUrl(_url: String) {

  val urlBuilder = UrlBuilder.fromString(_url)

  val domainName = httpHost.getHostName

  val baseUrl = httpHost.toURI

  val httpHost = URIUtils.extractHost(new URI(toString))

  override def toString = urlBuilder.toString

  /**
   * Maximum is 1 and minimum is 0.
   */
  var score: Double = 0d

  override def equals(obj: Any) = obj.isInstanceOf[WebUrl] && obj.asInstanceOf[WebUrl].toString == toString

  override def hashCode() = toString.hashCode
}
