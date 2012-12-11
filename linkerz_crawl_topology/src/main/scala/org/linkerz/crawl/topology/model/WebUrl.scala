/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.model

import org.apache.http.client.utils.URIUtils
import java.net.URI
import grizzled.slf4j.Logging
import gumi.builders.UrlBuilder

/**
 * The Class WebUrl.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:11 AM
 *
 */

class WebUrl(_url: String) extends Logging with Serializable {

  def urlBuilder = UrlBuilder.fromString(_url)

  /**
   * Redirect url. in case response code is 301 and 302
   */
  var movedToUrl: String = _

  def domainName = httpHost.getHostName

  def baseUrl = httpHost.toURI

  def url = urlBuilder.toString

  def httpHost = URIUtils.extractHost(new URI(url))

  override def equals(obj: Any) = {
    obj.isInstanceOf[WebUrl] && obj.asInstanceOf[WebUrl].url == url
  }

  override def hashCode() = url.hashCode
}
