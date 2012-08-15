/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.util

import org.springframework.web.util.UriComponentsBuilder
import org.apache.commons.lang.StringUtils

/**
 * The Class UrlUtils.
 *
 * @author Nguyen Duc Dung
 * @since 8/16/12, 3:23 AM
 *
 */

object UrlUtils {


  /**
   * make http://abc.net => http://abc.net/
   * make http://abc.net/bcd => http://abc.net/bcd/
   * @param url
   */
  def normalize(url: String): String = {
    assert(StringUtils.isNotBlank(url))
    val uri = UriComponentsBuilder.fromHttpUrl(url).build()
    if (uri.getQueryParams.isEmpty && StringUtils.isBlank(uri.getFragment)) {
      if (StringUtils.isBlank(uri.getPath)) {
        return url.trim.toLowerCase + '/'
      } else if (!uri.getPath.contains('.')) {
        val lastChar = uri.getPath.charAt(uri.getPath.length - 1)
        if (lastChar != '/') {
          return url.trim.toLowerCase + '/'
        }
      }
    }
    url.trim.toLowerCase
  }


}
