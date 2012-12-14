/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.model

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

class WebPage extends Serializable {

  var webUrl: WebUrl = _
  //Using java list for better performance.
  var webUrls: java.util.List[WebUrl] = _
  var content: Array[Byte] = Array.empty[Byte]

  var responseCode: Int = _

  var isRedirect = false

  //Meta data
  var text: Option[String] = None
  var contentType: String = _
  var contentEncoding: String = _
  var title: String = _
//  var description: Option[String] = None
  var featureImageUrl: Option[String] = None
  var featureImage: Option[Array[Byte]] = None

  var parsed: Boolean = false

  /**
   * Check the is any error on this page.
   * @return
   */
  def isError = {
    responseCode != HttpStatus.SC_OK
  }

  /**
   * Convenient method to convert a webpage to link model to store the database.
   * @return
   */
  def asLink = Link(
    url = webUrl.url,
    title = title,
    text = text,
//    description = description,
    featureImageUrl = featureImageUrl,
    contentEncoding = contentEncoding,
    responseCode = responseCode,
    featureImage = featureImage,
    parsed = parsed,
    indexDate = new util.Date
  )
}
