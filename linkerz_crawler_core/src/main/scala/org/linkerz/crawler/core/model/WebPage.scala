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
  var content: Array[Byte] = Array.empty[Byte]

  var responseCode: Int = _

  //Meta data
  var text: Option[String] = None
  var contentType: String = _
  var contentEncoding: String = _
  var title: String = _
  //  var description: Option[String] = None
  var featureImageUrl: Option[String] = None
  var featureImage: Option[Array[Byte]] = None

  var parsed: Boolean = false

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
  def asLink = {
    val link = new Link
    link.url = webUrl.url
    link.content = content
    link.title = title
    link.text = text
    //    description = description,
    link.featureImageUrl = featureImageUrl
    link.contentEncoding = contentEncoding
    link.responseCode = responseCode
    link.featureImage = featureImage
    link.parsed = parsed
    link.indexDate = new util.Date

    link
  }

}
