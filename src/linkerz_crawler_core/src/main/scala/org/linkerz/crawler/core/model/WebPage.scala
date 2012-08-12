/*
 * Copyright (C) 2012 - 2013 LinkerZ
 */

package org.linkerz.crawler.core.model

import org.linkerz.mongodb.model.Link
import java.util

/**
 * The Class WebPage.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:10 AM
 *
 */

class WebPage {

  var webUrl: WebUrl = _
  var webUrls: List[WebUrl] = _
  var content: Array[Byte] = _

  //Meta data
  var contentEncoding: String = _
  var title: String = _
  var description: String = _
  var featureImageUrl: String = _

  var parent: WebPage = _

  /**
   * Convenient method to convert a webpage to link model to store the database.
   * @return
   */
  def asLink() = {
    val link = new Link
    link.url = webUrl.url
    link.content = content
    link.title = title
    link.description = description
    link.featureImageUrl = featureImageUrl
    link.indexDate = new util.Date
    link.contentEncoding = contentEncoding
    link
  }
}
