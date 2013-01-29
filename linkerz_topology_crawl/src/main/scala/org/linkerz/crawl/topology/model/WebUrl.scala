/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.model

import org.apache.http.client.utils.URIUtils
import java.net.URI
import gumi.builders.UrlBuilder
import org.linkerz.parser.model.Link

/**
 * The Class WebUrl.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:11 AM
 *
 */

class WebUrl(_url: String) extends Serializable {

  def this(link: Link) {
    this(link.url)
    score = link.score
  }

  /**
   * Maximum is 1 and minimum is 0.
   */
  var score: Double = 0d

  def urlBuilder = UrlBuilder.fromString(_url)

  def domainName = httpHost.getHostName

  def baseUrl = httpHost.toURI

  override def toString = urlBuilder.toString

  def httpHost = URIUtils.extractHost(new URI(toString))

  override def equals(obj: Any) = {
    obj.isInstanceOf[WebUrl] && obj.asInstanceOf[WebUrl].toString == toString
  }

  override def hashCode() = toString.hashCode
}
