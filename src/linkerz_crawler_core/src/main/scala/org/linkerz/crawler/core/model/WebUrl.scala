/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.model

import com.googlecode.flaxcrawler.utils.UrlUtils
import reflect.BeanProperty

/**
 * The Class WebUrl.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:11 AM
 *
 */

case class WebUrl(private val _url: String) {

  def domainName = UrlUtils.getDomainName(url)

  val url = _url.trim.toLowerCase

  override def equals(obj: Any) = {
    obj.isInstanceOf[WebUrl] && obj.asInstanceOf[WebUrl].url == url
  }

  override def hashCode() = url.hashCode
}
