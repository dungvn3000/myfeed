/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.model

import org.apache.http.client.utils.URIUtils
import java.net.URI
import gumi.builders.UrlBuilder
import org.linkerz.core.string.RichString._

/**
 * The Class WebUrl.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:11 AM
 *
 */

case class WebUrl(url: String) {

  /**
   * Maximum is 1 and minimum is 0.
   */
  var score: Double = 0d

  def urlBuilder = UrlBuilder.fromString(url.trimToEmpty)

  def domainName = httpHost.getHostName

  def baseUrl = httpHost.toURI

  override def toString = urlBuilder.toString

  def httpHost = URIUtils.extractHost(new URI(toString))

  override def equals(obj: Any) = {
    obj.isInstanceOf[WebUrl] && obj.asInstanceOf[WebUrl].toString == toString
  }

  override def hashCode() = toString.hashCode
}
