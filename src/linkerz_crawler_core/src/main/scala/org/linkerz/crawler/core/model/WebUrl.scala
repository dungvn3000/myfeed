/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.model

import org.apache.http.client.utils.URIUtils
import java.net.URI
import org.linkerz.crawler.core.util.UrlUtils

/**
 * The Class WebUrl.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:11 AM
 *
 */

case class WebUrl(private val _url: String) {

  def domainName = URIUtils.extractHost(new URI(url)).getHostName

  val url = UrlUtils.normalize(_url)

  override def equals(obj: Any) = {
    obj.isInstanceOf[WebUrl] && obj.asInstanceOf[WebUrl].url == url
  }

  override def hashCode() = url.hashCode
}
