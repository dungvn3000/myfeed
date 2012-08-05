/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.model

import com.googlecode.flaxcrawler.utils.UrlUtils

/**
 * The Class WebUrl.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:11 AM
 *
 */

case class WebUrl(_url: String) {

  def domainName = UrlUtils.getDomainName(url)

  def url = _url.trim.toLowerCase

}
