/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.model

import org.apache.http.client.utils.URIUtils
import java.net.URI
import org.linkerz.crawler.core.util.UrlUtils
import org.apache.commons.validator.routines.UrlValidator

/**
 * The Class WebUrl.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:11 AM
 *
 */

class WebUrl(_url: String) {

  //Validate the url
  val validator = new UrlValidator
  assert(validator.isValid(_url))

  /**
   * Redirect url. in case response code is 301 and 302
   */
  var movedToUrl: String = _

  def domainName = URIUtils.extractHost(new URI(url)).getHostName

  val url = UrlUtils.normalize(_url)

  override def equals(obj: Any) = {
    obj.isInstanceOf[WebUrl] && obj.asInstanceOf[WebUrl].url == url
  }

  override def hashCode() = url.hashCode
}
