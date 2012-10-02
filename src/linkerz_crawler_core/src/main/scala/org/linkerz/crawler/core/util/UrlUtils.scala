/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.util

import org.springframework.web.util.UriComponentsBuilder
import org.apache.commons.lang.StringUtils
import edu.uci.ics.crawler4j.url.URLCanonicalizer
import java.net.URLDecoder

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
   * make http://abc.net//bcd => http://abc.net/bcd
   * @param url
   */
  def normalize(url: String): String = {
    assert(StringUtils.isNotBlank(url))
    val decodeUrl = URLDecoder.decode(url.trim, "UTF-8")
    val newURl = URLCanonicalizer.getCanonicalURL(decodeUrl)
    if (StringUtils.isBlank(newURl)) {
      return url
    }
    val uri = UriComponentsBuilder.fromHttpUrl(newURl).build()
    if (uri.getQueryParams.isEmpty && StringUtils.isBlank(uri.getFragment)) {
      if (StringUtils.isBlank(uri.getPath)) {
        return uri.toString + '/'
      } else if (!uri.getPath.contains('.')) {
        val lastChar = uri.getPath.charAt(uri.getPath.length - 1)
        if (lastChar != '/') {
          return uri.toString + '/'
        }
      }
    }
    uri.toString
  }
}
