/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.model

import java.util
import org.apache.http.HttpStatus
import org.linkerz.model.Link
import org.bson.types.ObjectId

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
  var webUrls: java.util.List[WebUrl] = new util.ArrayList[WebUrl]()
  var content: Array[Byte] = Array.empty[Byte]

  var responseCode: Int = _

  //Meta data
  var text: Option[String] = None
  var description: Option[String] = None
  var contentType: String = _
  var contentEncoding: String = "UTF-8"
  var title: String = _
  var featureImage: Option[Array[Byte]] = None
  var potentialImages: List[String] = Nil
  var feedId: ObjectId = _

  var isArticle = false

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
    description = description,
    contentEncoding = contentEncoding,
    responseCode = responseCode,
    featureImage = featureImage,
    feedId = feedId,
    indexDate = new util.Date
  )
}
