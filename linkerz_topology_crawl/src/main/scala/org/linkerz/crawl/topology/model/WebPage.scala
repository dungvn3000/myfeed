/*
 * Copyright (C) 2012 - 2013 LinkerZ (Searching and Sharing)
 */

package org.linkerz.crawl.topology.model

import org.linkerz.model.Link
import org.bson.types.ObjectId

/**
 * The Class WebPage.
 *
 * @author Nguyen Duc Dung
 * @since 7/29/12, 1:10 AM
 *
 */

case class WebPage(webUrl: WebUrl) {

  var content: Array[Byte] = Array.empty[Byte]

  //Meta data
  var text: Option[String] = None
  var description: Option[String] = None
  var contentType: String = _
  var contentEncoding: String = "UTF-8"
  var title: String = _
  var featureImage: Option[Array[Byte]] = None
  var potentialImages: List[String] = Nil

  var isArticle = false

  /**
   * Score of a webpage is score of it's url.
   * @return
   */
  def score: Double = webUrl.score

  /**
   * Convenient method to convert a webpage to link model to store the database.
   * @return
   */
  def asLink(feedId: ObjectId) = Link(
    url = urlAsString,
    title = title,
    text = text,
    score = score,
    description = description,
    contentEncoding = contentEncoding,
    featureImage = featureImage,
    feedId = feedId
  )

  /**
   * Convenient method.
   * @return
   */
  def urlAsString = webUrl.toString
}
