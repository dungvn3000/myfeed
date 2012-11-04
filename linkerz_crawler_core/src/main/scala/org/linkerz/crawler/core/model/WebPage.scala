/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawler.core.model

import java.util
import org.apache.http.HttpStatus
import org.linkerz.model.Link

/**
 * The Class WebPage.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:10 AM
 *
 */

class WebPage {

  var webUrl: WebUrl = _
  //Using java list for better performance.
  var webUrls: java.util.List[WebUrl] = _
  var content: Array[Byte] = _

  var responseCode: Int = _

  //Meta data
  var contentType: String = _
  var contentEncoding: String = _
  var title: String = _
  var description: String = _
  var featureImageUrl: String = _
  var featureImage: Array[Byte] = _

  var parent: WebPage = _

  /**
   * Check the is any error on this page.
   * @return
   */
  def isError = {
    responseCode != HttpStatus.SC_OK
  }


  def isRedirect = {
    responseCode == HttpStatus.SC_MOVED_PERMANENTLY || responseCode == HttpStatus.SC_MOVED_TEMPORARILY
  }

  /**
   * Convenient method to convert a webpage to link model to store the database.
   * @return
   */
  def toLink = Link(
    url = webUrl.url,
    content = content,
    title = title,
    description = description,
    featureImageUrl = featureImageUrl,
    contentEncoding = contentEncoding,
    responseCode = responseCode,
    featureImage = featureImage,
    indexDate = new util.Date
  )
}
